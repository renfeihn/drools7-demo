package com.sample;


import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;


public class Test {


    public static void main(String[] args) {
        KieHelper helper = new KieHelper();

        helper.addResource(ResourceFactory.newClassPathResource("com/wm/ruleFlow.drl"), ResourceType.DRL);
        helper.addResource(ResourceFactory.newClassPathResource("com/wm/ruleflowed.rf"), ResourceType.DRF);


        KieSession kSession = helper.build().newKieSession();
        RuleFlow rf = new RuleFlow();
        rf.setUserName("小王");
        rf.setSex("男");
        rf.setAge(18);
        rf.setSource(88);
        kSession.insert(rf);
        kSession.startProcess("test");
        kSession.fireAllRules();
        kSession.dispose();
    }

}
