package com.sample;


import lombok.extern.slf4j.Slf4j;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class DroolsTest {


    @Test
    public void TestRf() {
        KieHelper helper = new KieHelper();

        helper.addResource(ResourceFactory.newClassPathResource("com/wm/ruleFlow.drl"), ResourceType.DRL);
        helper.addResource(ResourceFactory.newClassPathResource("com/wm/ruleflowed.rf"), ResourceType.DRF);

        KieSession kSession = helper.build().newKieSession();

        RuleFlow rf = new RuleFlow();
        rf.setUserName("wangmm");
        rf.setSex("男");
        rf.setAge(18);
        rf.setSource(88);
        kSession.insert(rf);

        kSession.startProcess("test");
        kSession.fireAllRules();
        kSession.dispose();
    }

    @Test
    public void TestBpmn() {
        KieHelper helper = new KieHelper();

        helper.addResource(ResourceFactory.newClassPathResource("com/wm/ruleFlow.drl"), ResourceType.DRL);
//        helper.addResource(ResourceFactory.newClassPathResource("ruleflowed.rf"), ResourceType.DRF);
        helper.addResource(ResourceFactory.newClassPathResource("com/wm/ruleflowed.bpmn"), ResourceType.BPMN2);

        KieSession kSession = helper.build().newKieSession();

        RuleFlow rf = new RuleFlow();
        rf.setUserName("wangm");
        rf.setSex("男");
        rf.setAge(18);
        rf.setSource(88);
        kSession.insert(rf);

        kSession.startProcess("test");
        kSession.fireAllRules();
        kSession.dispose();
    }

    @Test
    public void TestFirst() {

        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String rules = compiler.compile(ResourceFactory.newClassPathResource("com/wm/First.xlsx"), "rule-table");
        System.out.println(rules);


        KieHelper helper = new KieHelper();

        helper.addResource(ResourceFactory.newClassPathResource("com/wm/First.xlsx"), ResourceType.DTABLE);
        helper.addResource(ResourceFactory.newClassPathResource("com/wm/First.bpmn"), ResourceType.BPMN2);

        KieSession kSession = helper.build().newKieSession();

//		RuleFlow rf = new RuleFlow();
//		rf.setUserName("wangm");
//		rf.setSex("男");
//		rf.setAge(14);
//		rf.setSource(88);
//        kSession.insert(rf);

        Map<String, Object> map = new HashMap<>();
        map.put("userName", "wangm");
        map.put("age", 14);

        kSession.insert(map);
        kSession.startProcess("test");
        kSession.fireAllRules();

        System.out.println("msg:"+map.get("msg"));

        kSession.dispose();
    }


    @Test
    public void getRuleTable() {
        //把excel翻译成drl文件
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String rules = compiler.compile(ResourceFactory.newClassPathResource("com/wm/First.xlsx"), "rule-table");
        System.out.println(rules);
    }

}
