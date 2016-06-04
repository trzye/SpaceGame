package pl.edu.pw.ee.spacegame.server.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.spacegame.server.dao.crud.LogsDAO;
import pl.edu.pw.ee.spacegame.server.entity.LogsEntity;

/**
 * Created by Micha³ on 2016-06-04.
 */
@Repository
public class DatabaseLogger {

    @Autowired
    private LogsDAO logsDAO;

    private Class clazz;

    public DatabaseLogger() {
        this.clazz = getClass();
    }

    public void error(String information) {
        Logger logger = Logger.getLogger(clazz);
        logger.error(information);
        logToDatabase(information, LogLevel.ERROR);
    }

    public void info(String information) {
        Logger logger = Logger.getLogger(clazz);
        logger.info(information);
        logToDatabase(information, LogLevel.INFO);
    }

    public void debug(String information) {
        Logger logger = Logger.getLogger(clazz);
        logger.debug(information);
        logToDatabase(information, LogLevel.DEBUG);
    }

    private void logToDatabase(String information, LogLevel logLevel) {
        try {
            LogsEntity logsEntity = new LogsEntity();
            logsEntity.setClazz(clazz.getSimpleName());
            logsEntity.setInformation(information);
            logsEntity.setLevel(logLevel.name());
            logsDAO.save(logsEntity);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass());
            logger.error(UtilsConstantObjects.SQL_LOGGING_ERROR);
            e.printStackTrace();
        }
    }

    public void setClass(Class clazz) {
        this.clazz = clazz;
    }
}
