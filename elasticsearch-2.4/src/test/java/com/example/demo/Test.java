package com.example.demo;

import com.example.demo.domain.MetadataTable;
import com.example.demo.domain.TableProperties;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test {
    @Autowired
    private ElasticsearchTemplate template;

    @org.junit.Test
    public void test() throws IOException {
//        boolean b = template.typeExists("cskefu", "chat_message2");
//        System.out.println(b);
        MetadataTable tb = new MetadataTable();
        tb.setTablename("uk_contacts");
        List<TableProperties> tableProperties = tb.getTableproperty();
        TableProperties tableProperty = new TableProperties();
        String orgi = "cskefu";
        XContentBuilder builder = jsonBuilder().startObject()
                .startObject(tb.getTablename().toLowerCase())
                .startObject("properties");
        for (TableProperties tp : tb.getTableproperty()) {
            builder.startObject(tp.getFieldname().toLowerCase());
            if (tp.getDatatypename().equalsIgnoreCase("text") && !tp.getFieldname().equalsIgnoreCase("id")) {
                builder.field("type", "string").field("index", tp.isToken() ? "analyzed" : "not_analyzed");
                if (tp.isToken() && "keyword".equalsIgnoreCase(tp.getTokentype())) {
                    builder.field("analyzer", "whitespace");
                }
                if (!tp.isToken()) {
                    builder.field("ignore_above", "256");
                }
            } else if (tp.getDatatypename().toLowerCase().equals("date")) {
                builder.field("type", "long").field("index", "not_analyzed");
            } else if (tp.getDatatypename().toLowerCase().equals("datetime")) {
                builder.field("type", "long").field("index", "not_analyzed");
            } else if (tp.getDatatypename().toLowerCase().equals("long")) {
                builder.field("type", "long").field("index", "not_analyzed");
            } else if (tp.getDatatypename().toLowerCase().equals("textarea")) {
                builder.field("type", "string").field("index", "analyzed");
            } else if (tp.getDatatypename().toLowerCase().equals("nlp")) {
                builder.field("type", "string").field("index", "not_analyzed").field("ignore_above", "256");
            } else if (tp.getDatatypename().toLowerCase().equals("url")) {
                builder.field("type", "string").field("index", "not_analyzed");
            } else if (tp.getDatatypename().toLowerCase().equals("email")) {
                builder.field("type", "string").field("index", "not_analyzed");
            } else if (tp.getDatatypename().toLowerCase().equals("number")) {
                builder.field("type", "float").field("index", "not_analyzed");
            } else if (tp.getDatatypename().toLowerCase().equals("boolean")) {
                builder.field("type", "boolean").field("index", "not_analyzed");
            } else {
                builder.field("type", "string").field("index", tp.isToken() ? "analyzed" : "not_analyzed");
            }
            builder.endObject();
        }
        builder.endObject().endObject().endObject();
        template.putMapping("cskefu", tb.getTablename(), builder);
    }

}