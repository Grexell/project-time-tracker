package by.dima.database.controller;

import com.smattme.MysqlExportService;
import com.smattme.MysqlImportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@RestController
@RequestMapping("/database")
public class DatabaseController {

    private final MysqlExportService mysqlExportService;
    private final MysqlImportService mysqlImportService;

    public DatabaseController(@Value("${database.url}") String mysqlUrl,
                              @Value("${database.username}") String username,
                              @Value("${database.password}") String password) {
        Properties properties = new Properties();
        properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, mysqlUrl);
        properties.setProperty(MysqlExportService.DB_USERNAME, username);
        properties.setProperty(MysqlExportService.DB_PASSWORD, password);
        properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
        properties.setProperty(MysqlExportService.PRESERVE_GENERATED_SQL_FILE, "true");
        properties.setProperty(MysqlExportService.ADD_IF_NOT_EXISTS, "false");
        properties.setProperty(MysqlExportService.TEMP_DIR, new File("backup").getPath());

        mysqlExportService = new MysqlExportService(properties);

        mysqlImportService = MysqlImportService.builder()
                .setDeleteExisting(true)
                .setDropExisting(true)
                .setJdbcConnString(mysqlUrl)
                .setUsername(username)
                .setPassword(password);

    }

    @PostMapping(value = "backup", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<Resource> backup() throws IOException, SQLException, ClassNotFoundException {
        mysqlExportService.export();
        String generatedSql = "SET SESSION FOREIGN_KEY_CHECKS=0;" + mysqlExportService.getGeneratedSql();
        mysqlExportService.clearTempFiles();
        return Mono.just(new ByteArrayResource(generatedSql.getBytes()));
    }

    @PostMapping(value = "restore", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> restore(@RequestPart("file") FilePart filePart) {
        return filePart.content()
                .map(content -> new String(content.asByteBuffer().array()))
                .map(content -> {
                    try {
                        return mysqlImportService.setSqlString(content).importDatabase();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }).then();
    }
}
