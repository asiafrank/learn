package rmi;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.rmi.RemoteException;

public class ServiceMain {
    public static void main(String[] args) {
        System.out.println("Service start");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("rmi-service.xml");
        RmiServiceExporter rse = context.getBean(RmiServiceExporter.class);
        try {
            rse.prepare();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}