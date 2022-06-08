package edu.yu.cs.com1320.project.stage1;

import edu.yu.cs.com1320.project.stage2.DocumentStore.DocumentFormat;
import edu.yu.cs.com1320.project.stage2.impl.DocumentStoreImpl;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentStoreAPITest {

    @Test
    public void fieldCount() {
        Field[] fields = DocumentStoreImpl.class.getFields();
        int publicFieldCount = 0;
        for (Field field : fields) {
            if (Modifier.isPublic(field.getModifiers())) {
                publicFieldCount++;
            }
        }
        assertTrue(publicFieldCount == 0);
    }

    @Test
    public void subClassCount() {
        @SuppressWarnings("rawtypes")
        Class[] classes = DocumentStoreImpl.class.getClasses();
        assertTrue(classes.length == 0);
    }

    @Test
    public void constructorExists() {
        try {
            new DocumentStoreImpl();
        } catch (Exception e) {}
    }

    @Test
    public void putDocumentExists() throws URISyntaxException{
        try {
            new DocumentStoreImpl().putDocument(null, new URI("hi"), DocumentFormat.BINARY);
        } catch (Exception e) {}
    }

    @Test
    public void getDocumentExists() throws URISyntaxException{
        try {
            new DocumentStoreImpl().getDocument(new URI("hi"));
        } catch (Exception e) {}
    }

    @Test
    public void deleteDocumentExists() throws URISyntaxException {
        try {
            new DocumentStoreImpl().deleteDocument(new URI("hi"));
        } catch (Exception e) {}
    }

}
