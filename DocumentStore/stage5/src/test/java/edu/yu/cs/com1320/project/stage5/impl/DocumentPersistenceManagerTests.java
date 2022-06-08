package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.stage5.Document;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class DocumentPersistenceManagerTests {
    private File directory;
    private URI uri;
    private URI uri1;
    private Document document;
    private DocumentPersistenceManager manager;

    @BeforeEach
    public void initialize () throws IOException {
        this.directory = new File("C:/Users/josephborodach/temp/new/hello/by");
        this.uri = URI.create("https://www.google.com/");
        this.uri1 = URI.create("https://stackoverflow.com/questions/15620835/gson-fromjson-throw-exception-if-type-is-different");
        this.document = new DocumentImpl(this.uri, "borodach, welcome".getBytes());
    }

    @Test
    public void sereilizeTest() throws IOException {
        assertDoesNotThrow (() -> this.manager = new DocumentPersistenceManager(this.directory));
        assertDoesNotThrow (() -> manager.serialize(this.uri, this.document));
        Document document1 = manager.deserialize(this.uri);
        assertEquals(this.document, document1);
    }

    @Test
    public void deleteTest() throws IOException {
        assertDoesNotThrow (() -> this.manager = new DocumentPersistenceManager(this.directory));
        assertFalse(this.manager.delete(null));
        assertFalse(this.manager.delete(this.uri));
        assertDoesNotThrow (() -> manager.serialize(this.uri, this.document));
        assertTrue(this.manager.delete(this.uri));
        assertFalse(this.manager.delete(this.uri));
    }

    @Test
    public void exceptionsTest () {
        assertDoesNotThrow (() -> this.manager = new DocumentPersistenceManager(this.directory));
        //Should throw an exception because the URI is no longer there
        assertThrows(IllegalArgumentException.class, () -> manager.serialize(this.uri, null));
    }

}
