package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Loggerhelp {
	public static Logger getLogger(Class<?> cls) {
        return LogManager.getLogger(cls);
    }
}
