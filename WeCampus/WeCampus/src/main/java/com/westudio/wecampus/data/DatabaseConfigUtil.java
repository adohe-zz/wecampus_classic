package com.westudio.wecampus.data;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by nankonami on 13-9-9.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    /**
     * Write database configuration txt
     * @param args
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) throws IOException, SQLException {
        writeConfigFile("ormlite_config.txt");
    }
}
