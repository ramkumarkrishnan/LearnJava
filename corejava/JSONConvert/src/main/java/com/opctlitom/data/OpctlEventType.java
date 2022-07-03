package com.opctlitom.data;

public enum OpctlEventType {
    APPROVE_ACCESS("com.oraclecloud.operatorcontrol.approveaccessrequest")
    , AUTO_APPROVE_ACCESS(
        "com.oraclecloud.operatorcontrol.autoapproveaccessrequest")
    , CLOSED_ACCESS("com.oraclecloud.operatorcontrol.closedaccessrequest")
    , CREATE_ACCESS("com.oraclecloud.operatorcontrol.createaccessrequest")
    , CREATE_OPERATOR_CONTROL(
        "com.oraclecloud.operatorcontrol.createoperatorcontrol")
    , CREATE_OPERATOR_CONTROL_ASSIGNMENT(
        "com.oraclecloud.operatorcontrol.createoperatorcontrolassignment")
    , DELETE_OPERATOR_CONTROL(
        "com.oraclecloud.operatorcontrol.deleteoperatorcontrol")
    , DELETE_OPERATOR_CONTROL_ASSIGNMENT(
        "com.oraclecloud.operatorcontrol.deleteoperatorcontrolassignment")
    , EXPIRED_ACCESS("com.oraclecloud.operatorcontrol.expiredaccessrequest")
    , EXTEND_ACCESS("com.oraclecloud.operatorcontrol.extendaccessrequest")
    , REJECT_ACCESS("com.oraclecloud.operatorcontrol.rejectaccessrequest")
    , REVOKE_ACCESS("com.oraclecloud.operatorcontrol.revokeaccessrequest");

    private String accessRequest;

    OpctlEventType(String accessRequest) {
        this.accessRequest = accessRequest;
    }

    public String getAccessRequest() {
        return accessRequest;
    }
}
