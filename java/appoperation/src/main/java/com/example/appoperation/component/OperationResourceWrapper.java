package com.example.appoperation.component;

import com.example.appoperation.db.po.OperationResourcePO;
import com.example.appoperation.db.po.UserClassificationConditionPO;
import lombok.Data;

import java.util.List;

/**
 * @author zhangxiaofan 2021/01/14-15:56
 */
@Data
public class OperationResourceWrapper {
    private OperationResourcePO resourcePO;
    private List<UserClassificationConditionPO> rulePO;
}
