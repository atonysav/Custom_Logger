import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum Logger {
    INSTANCE;

    enum WorkMode {
        RElEASE, DEBUG;
    }

    final WorkMode workMode;
    private File[] logFiles;
    private FileOutputStream[] fosArr;

    Logger() {
        File file = new File("logConfig.cfg");
        WorkMode temp = WorkMode.RElEASE;
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] arrByte = new byte[fis.available()];
                fis.read(arrByte);
                String strOne = new String(arrByte);
                if ("debug".equals(strOne.toLowerCase())) {
                    temp = WorkMode.DEBUG;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        workMode = temp;
        fileInitializer();
        fileOutputStreamsInitializer();
    }

    private void fileInitializer() {
        LogTypes[] types = LogTypes.values();
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = format.format(currentDate);
        logFiles = new File[types.length + 1];
        for (int i = 0; i < logFiles.length - 1; i++) {
            logFiles[i] = new File(types[i] + "_" + dateString + "_log.log");
            if (!logFiles[i].exists()) {
                try {
                    logFiles[i].createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        logFiles[logFiles.length - 1] = new File("All_" + dateString + "_log.log");
        if (!logFiles[logFiles.length - 1].exists()) {
            try {
                logFiles[logFiles.length - 1].createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void fileOutputStreamsInitializer() {
        fosArr = new FileOutputStream[logFiles.length];
        for (int i = 0; i < fosArr.length; i++) {
            try {
                fosArr[i] = new FileOutputStream(logFiles[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void log(LogTypes logType, String message){
        byte index = getIndex(logType);
        if(index==-1){
            return;
        }
        try {
            fosArr[index].write(message.getBytes());
            fosArr[fosArr.length-1].write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte getIndex(LogTypes logType){
        LogTypes logTypes[] = LogTypes.values();
        for (byte i = 0; i < logTypes.length; i++) {
            if(logTypes[i].equals(logType))
                return i;
        }
        return -1;
    }

    /*private String messageFormalizer(LogTypes type, String message){

    }*/

    public void close(){
        for (FileOutputStream stream:fosArr) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
