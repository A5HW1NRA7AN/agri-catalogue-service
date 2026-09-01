package com.catalogue.verg.core.util;

import com.catalogue.verg.core.constants.NotificationTemplateConstants;
import com.catalogue.verg.core.constants.NotificationTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class NotificationTemplateResolver {

    private NotificationTemplateResolver() {
        // Utility class
    }

    public static NotificationTemplate resolveDecisionTemplate(
            String operation,
            String targetStatus
    ) {

        // Review means the record is pending with L2
        boolean isL2 = "review".equalsIgnoreCase(operation);

        if (Constants.REJECTED.equals(targetStatus)) {

            log.info(
                    "Resolving notification template: " +
                            "RECORD_REJECTED_BY_ADMIN_L2 / " +
                            "RECORD_REJECTED_BY_SUPERVISOR"
            );

            return isL2
                    ? NotificationTemplateConstants.RECORD_REJECTED_BY_ADMIN_L2
                    : NotificationTemplateConstants.RECORD_REJECTED_BY_SUPERVISOR;
        }

        if (Constants.REWORK.equals(targetStatus)) {

            log.info(
                    "Resolving notification template: " +
                            "RECORD_SENT_BACK_FOR_CORRECTION"
            );

            return NotificationTemplateConstants.RECORD_SENT_BACK_FOR_CORRECTION;
        }

        // Approve:
        // PENDING -> APPROVED
        //
        // Review:
        // APPROVED -> ACTIVE
        return isL2
                ? NotificationTemplateConstants.RECORD_APPROVED_BY_ADMIN_L2
                : NotificationTemplateConstants.RECORD_APPROVED_BY_SUPERVISOR;
    }
}