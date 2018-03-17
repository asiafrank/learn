package com.asiafrank.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.push;

public class Hello {
    public static void main(String[] args) {

        // Connect to MongoDB
        MongoCredential mongoCredential = MongoCredential.createCredential("test", "test", "123456".toCharArray());
        List<MongoCredential> cres = new ArrayList<MongoCredential>();
        cres.add(mongoCredential);

        MongoClient mongo = new MongoClient(new ServerAddress("localhost:27017"), cres);

        MongoDatabase db = mongo.getDatabase("test");


        System.out.println("--dbnames--");
        for (String dbname : mongo.listDatabaseNames()) {
            System.out.println(dbname);
        }

        System.out.println("--collections--");
        for (String coll : db.listCollectionNames()) {
            System.out.println(coll);
        }

        MongoCollection<Document> collection = db.getCollection("users");

        System.out.println("--documents--");
        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());
        }

        // insert
        Document doc = new Document();
        doc.put("name", "Frank");
        doc.put("age", 25);
        doc.put("sex", "male");
        doc.put("location", "Hangzhou");
        doc.put("emails", Arrays.asList("zhangxf@uyunsoft.cn", "zhangxfdev@gmail.com"));
        collection.insertOne(doc);

        System.out.println("--after insertion--");
        for (Document doc2 : collection.find()) {
            System.out.println(doc2.toJson());
        }

        // update
        collection.updateOne(eq("name", "Frank"), push("emails", "531072536@qq.com"));

        // filter
        Document d = collection.find(and(eq("name", "Frank"), gt("age", 10))).first();
        System.out.println("find first: " + d.toJson());

        // delete
        collection.deleteMany(eq("name", "Frank"));
    }
}
