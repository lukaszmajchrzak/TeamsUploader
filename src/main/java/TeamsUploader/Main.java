package TeamsUploader;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("appConfig.xml");
        ReportsReader reportsReader = context.getBean("ReportsReader", ReportsReader.class);
        ReportFileManager reportFileManager = context.getBean("ReportFileManager", ReportFileManager.class);

        ArrayList<Report> reports = reportsReader.readReports();

        for(int i=0;i<reports.size();i++){
            reportFileManager.runFilesOrganizer(reports.get(i));
        }
    }
}
