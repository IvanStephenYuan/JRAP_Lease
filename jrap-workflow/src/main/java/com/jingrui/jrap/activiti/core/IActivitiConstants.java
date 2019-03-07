package com.jingrui.jrap.activiti.core;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface IActivitiConstants {

    String ACT_RETRACT = "RETRACT";
    String ACT_STOP = "STOP";
    String PROP_COMMENT = "comment";
    String COMMENT_ACTION = "action";
    String COMMENT_COMPLETE_BY = "complete_by";
    String COMMENT_DELEGATE_BY = "delegate_by";
    String COMMENT_ADD_SIGN_BY = "add_sign_by";

    String PROP_APPROVE_RESULT = "approveResult";

    String APPROVED = "APPROVED";
    String REJECTED = "REJECTED";
    String DELEGATE = "DELEGATE";
    String AUTO_DELEGATE = "AUTO_DELEGATE";
    String ADD_SIGN = "ADD_SIGN";
    String JUMP = "JUMP";
    String RECALL = "RECALL";
    String CARBON_COPY = "CARBON_COPY";

    String ACTION_JUMP = "jump";
    String ACTION_ADD_SIGN = "addSign";

    String ACTION_CARBON_COPY = "carbonCopy";


    String NUMBER_OF_INSTANCES = "nrOfInstances";
    String NUMBER_OF_ACTIVE_INSTANCES = "nrOfActiveInstances";
    String NUMBER_OF_COMPLETED_INSTANCES = "nrOfCompletedInstances";

    String NUMBER_OF_APPROVED = "nrOfApproved";
    String NUMBER_OF_REJECTED = "nrOfRejected";

    String HISTORY_SUFFIX = "__his__";

    String APPROVAL_STRATEGY = "APPROVAL_STRATEGY";
    String APPROVAL_CANDIDATE = "APPROVAL_CANDIDATE_RULE";

    String APPROVAL_CANDIDATE_EMP = "APPOINTED_EMPLOYEE";
    String APPROVAL_CANDIDATE_POS = "APPOINTED_POSITION";
    String APPROVAL_CANDIDATE_ROLE = "APPOINTED_ROLE";

    String APPROVAL_PARAMETER = "parameter";
    String APPROVAL_BUSINESS_RULE = "businessRules";

    String APPROVAL_CODE_EMPLOYEE = "employeeCode";
    String APPROVAL_CODE_POSITION = "positionCode";
    String APPROVAL_CODE_ROLE = "roleCode";

    String APPROVAL_COLLECTION = "approvalCandidates";

    String APPROVAL_RETRACT = "ACT_RETRACT";

    String APPROVAL_WFL_NUM = "WFL_NUM";
    String APPROVAL_ACTION = "APPROVAL_ACTION";
    String ADD_SIGN_FLAG = "ADD_SIGN_FLAG";
    String DELEGATE_FLAG = "DELEGATE_FLAG";
    String AUTO_CARBON_COPY_FLAG = "ACT_AUTO_CARBON_COPY";
    String REVOKE_ENABLE_FLAG = "ACT_REVOKE_ENABLE_FLAG";
    String REVOKE_ENABLE_FLAG_WITHOUT_ACT = "REVOKE_ENABLE_FLAG";
    String APPOINTED_POSITION = "APPOINTED_POSITION";
    String APPOINTED_EMPLOYEE = "APPOINTED_EMPLOYEE";

    String CHANNEL_CARBON_COPY = "wfl.carbon.copy";
    String MSG_PARM_USERS = "users";
    String MSG_PAEM_PROCESSINSTANCEID = "processInstanceId";

    String MULTI_INFO_START = "jrap.process.start";
    String MULTI_INFO_END = "jrap.process.end";

    default boolean isApproved(String v) {
        return APPROVED.equalsIgnoreCase(v);
    }

    default boolean isRejected(String v) {
        return REJECTED.equalsIgnoreCase(v);
    }
}
