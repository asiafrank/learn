package rmi;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ClientMain {
    public static void main(String[] args) {
        System.out.println("Client start");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("rmi-client.xml");
        SimpleObject so = context.getBean(SimpleObject.class);
        try {
            while (true) {
                System.out.println("========");
                Account a = new Account();
                a.setName("Frank");
                so.insert(a);
                System.out.println("---");
                so.getAccounts();
                System.out.println("========");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Client close");
        context.close();
    }
}