package com.catalogue.verg.core.util;

import com.catalogue.verg.core.constants.NotificationTemplateConstants;
import com.catalogue.verg.core.constants.NotificationTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class NotificationTemplateResolver {

    private NotificationTemplateResolver() {
        // Utility class
    }

    // Returns every template to send for the action
    public static List<NotificationTemplate> resolveDecisionTemplates(
            String operation,
            String targetStatus
    ) {

        // Review means the record is pending with L2
        boolean isL2 = "review".equalsIgnoreCase(operation);

        List<NotificationTemplate> templates = new ArrayList<>();

        if (Constants.REJECTED.equals(targetStatus)) {

            log.info(
                    "Resolving notification template(s) for rejection"
            );

            if (isL2) {

                // L2 rejection -> Maker
                templates.add(
                        NotificationTemplateConstants.RECORD_REJECTED_BY_ADMIN_L2
                );

                // L2 rejection -> Supervisor
                templates.add(
                        NotificationTemplateConstants.RECORD_REJECTED_BY_ADMIN_L2_TO_SUPERVISOR
                );

            } else {

                // Supervisor rejection -> Maker
                templates.add(
                        NotificationTemplateConstants.RECORD_REJECTED_BY_SUPERVISOR
                );
            }

            return templates;
        }

        if (Constants.REWORK.equals(targetStatus)) {

            log.info(
                    "Resolving notification template(s) for rework"
            );

            // Either level -> Maker
            templates.add(
                    NotificationTemplateConstants.RECORD_SENT_BACK_FOR_CORRECTION
            );

            if (isL2) {

                // L2 rework -> Supervisor
                templates.add(
                        NotificationTemplateConstants.RECORD_SENT_BACK_FOR_CORRECTION_BY_ADMIN
                );
            }

            return templates;
        }

        /*
         * Approve:
         *
         * L1 Supervisor:
         * PENDING -> APPROVED
         *
         * L2 Admin:
         * APPROVED -> ACTIVE
         */

        if (isL2) {

            // L2 approval -> Maker
            templates.add(
                    NotificationTemplateConstants.RECORD_APPROVED_BY_ADMIN_L2
            );

            // L2 approval -> Supervisor
            templates.add(
                    NotificationTemplateConstants.RECORD_APPROVED_BY_ADMIN_TO_SUPERVISOR
            );

        } else {

            // Supervisor approval -> L2 Admin
            templates.add(
                    NotificationTemplateConstants.RECORD_APPROVED_BY_SUPERVISOR
            );

            // Supervisor approval -> Maker
            templates.add(
                    NotificationTemplateConstants.RECORD_APPROVED_BY_SUPERVISOR_TO_MAKER
            );
        }

        return templates;
    }
}
