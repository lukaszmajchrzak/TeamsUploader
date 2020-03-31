package TeamsUploader;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportFileManager {
    private String downloadPath;

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    /**
     * Method checks source folder for specific reports (by filename).
     * When report is found then it moves old report from destination to backup folder and then moves new file to the destination.
     *
     * @param report comes from report list
     */
    public void runFilesOrganizer(Report report) {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        Date today = new Date();


        File listenedPath = new File(downloadPath);
        File destinationPath = new File(report.getReportDestination());
        File reportFile = null;
        File existingReport;
        Boolean isFound = false;

        report.getLogger().sendLog("Listening for report: " + report.getReportName() + " has started.");
        for (String fileName : listenedPath.list()) {
            if (fileName.equalsIgnoreCase(report.getReportName())) {
                reportFile = new File(downloadPath + ((char) 92) + ((char) 92) + fileName);
                isFound = true;
                report.getLogger().sendLog("File: " + report.getReportName() + " found in " + downloadPath);
            }
        }
        if (isFound) {

            for (String fileName : destinationPath.list()) {
                if (fileName.equalsIgnoreCase(report.getReportName())) {
                    String tday = dateFormat.format(today);
                    existingReport = new File(report.getReportDestination() + ((char) 92) + ((char) 92) + fileName);
                    if (report.isMoveOld()) {
                        if (report.isAddDate()) {
                            existingReport.renameTo(new File(report.getReportDestination() + ((char) 92) + ((char) 92) + "backup" + ((char) 92) + ((char) 92) + tday + ((char) 92) + ((char) 92) + fileName));
                            report.getLogger().sendLog("File: " + report.getReportName() + " moved from " + report.getReportDestination() + " to backup/" + today + "/ folder");
                        } else {
                            existingReport.renameTo(new File(report.getReportDestination() + ((char) 92) + ((char) 92) + "backup" + ((char) 92) + ((char) 92) + fileName));
                            report.getLogger().sendLog("File: " + report.getReportName() + " moved from " + report.getReportDestination() + " to backup/ folder");
                        }
                    } else {
                        existingReport.delete();
                        report.getLogger().sendLog("File: " + report.getReportName() + " from " + report.getReportDestination() + " deleted.");
                    }
                }


                if (reportFile != null) {
                    reportFile.renameTo(new File(report.getReportDestination() + ((char) 92) + ((char) 92) + report.getReportName()));
                    report.getLogger().sendLog("File: " + report.getReportName() + " has been moved to " + report.getReportDestination());
                } else report.getLogger().sendLog("File: " + report.getReportName() + " not found in " + downloadPath);
            }
        }
    }
}
