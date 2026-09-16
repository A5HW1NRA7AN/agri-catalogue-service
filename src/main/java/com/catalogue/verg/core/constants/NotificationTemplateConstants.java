package com.catalogue.verg.core.constants;

public final class NotificationTemplateConstants {

    private NotificationTemplateConstants() {
    }

    public static final NotificationTemplate NEW_RECORD_SUBMITTED_FOR_REVIEW =
            new NotificationTemplate(
                    "NEW_RECORD_SUBMITTED_FOR_REVIEW"
            );

    public static final NotificationTemplate RECORD_SENT_BACK_FOR_CORRECTION =
            new NotificationTemplate(
                    "RECORD_SENT_BACK_FOR_CORRECTION"
            );

    public static final NotificationTemplate RECORD_RESUBMITTED_FOR_REVIEW =
            new NotificationTemplate(
                    "RECORD_RESUBMITTED_FOR_REVIEW"
            );

    public static final NotificationTemplate RECORD_REJECTED_BY_SUPERVISOR =
            new NotificationTemplate(
                    "RECORD_REJECTED_BY_SUPERVISOR"
            );

    public static final NotificationTemplate RECORD_APPROVED_BY_SUPERVISOR =
            new NotificationTemplate(
                    "RECORD_APPROVED_BY_SUPERVISOR"
            );

    public static final NotificationTemplate RECORD_REVIEWED_BY_ADMIN_L2 =
            new NotificationTemplate(
                    "RECORD_REVIEWED_BY_ADMIN_L2"
            );

    public static final NotificationTemplate RECORD_REJECTED_BY_ADMIN_L2 =
            new NotificationTemplate(
                    "RECORD_REJECTED_BY_ADMIN_L2"
            );

    public static final NotificationTemplate RECORD_APPROVED_BY_ADMIN_L2 =
            new NotificationTemplate(
                    "RECORD_APPROVED_BY_ADMIN_L2"
            );

    // L2 rejection -> Supervisor
    public static final NotificationTemplate RECORD_REJECTED_BY_ADMIN_L2_TO_SUPERVISOR =
            new NotificationTemplate(
                    "RECORD_REJECTED_BY_ADMIN_L2_TO_SUPERVISOR"
            );

    // L2 rework -> Supervisor
    public static final NotificationTemplate RECORD_SENT_BACK_FOR_CORRECTION_BY_ADMIN =
            new NotificationTemplate(
                    "RECORD_SENT_BACK_FOR_CORRECTION_BY_ADMIN"
            );

    // L2 approval -> Supervisor
    public static final NotificationTemplate RECORD_APPROVED_BY_ADMIN_TO_SUPERVISOR =
            new NotificationTemplate(
                    "RECORD_APPROVED_BY_ADMIN_TO_SUPERVISOR"
            );

    // L1 Supervisor approval -> Maker
    public static final NotificationTemplate RECORD_APPROVED_BY_SUPERVISOR_TO_MAKER =
            new NotificationTemplate(
                    "RECORD_APPROVED_BY_SUPERVISOR_TO_MAKER"
            );
}
