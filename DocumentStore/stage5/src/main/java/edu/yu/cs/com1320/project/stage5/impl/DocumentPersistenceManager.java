package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;

import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Base64;

/**
 * Created by the document store and given to the BTree via a call to BTree.setPersistenceManager
 * GsonBuilder: Creates a GsonBuilder instance that can be used to build Gson with various configuration settings. GsonBuilder follows the builder pattern, and it is typically used by first invoking various configuration methods to set desired options, and finally calling create().
 * disableHtmlEscaping: By default, Gson escapes HTML characters such as < > etc. Use this option to configure Gson to pass-through HTML characters as is. Returns: a reference to this GsonBuilder object to fulfill the "Builder" pattern.
 * setPrettyPrinting: Configures Gson to output Json that fits in a page for pretty printing. Returns: a reference to this GsonBuilder object to fulfill the "Builder" pattern
 * registerTypeAdapter:
 *      Configures Gson for custom serialization or deserialization. This method combines the registration of an TypeAdapter, InstanceCreator, JsonSerializer, and a JsonDeserializer. It is best used when a single object typeAdapter implements all the required interfaces for custom serialization with Gson. If a type adapter was previously registered for the specified type, it is overwritten.
 *      This registers the type specified and no other types: you must manually register related types! For example, applications registering boolean.class should also register Boolean.class.
 *      Parameters: type - the type definition for the type adapter being registered. typeAdapter - This object must implement at least one of the TypeAdapter, InstanceCreator, JsonSerializer, and a JsonDeserializer interfaces.
 *      Returns: a reference to this GsonBuilder object to fulfill the "Builder" pattern
 * create: Creates a Gson instance based on the current configuration. This method is free of side-effects to this GsonBuilder instance and hence can be called multiple times. Returns: an instance of Gson configured with the options currently set in this builder
 */
public class DocumentPersistenceManager implements PersistenceManager<URI, Document> {
    private final File baseDir;
    private final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .registerTypeAdapter(byte[].class, new ByteAdapter()) //ByteAdapter
            .create();

    /**
     * getProperty: Gets the system property indicated by the specified key. First, if there is a security manager, its checkPropertyAccess method is called with the key as its argument. This may result in a SecurityException. If there is no current set of system properties, a set of system properties is first created and initialized in the same manner as for the getProperties method.
     * user.dir: This directory is named by the system property user.dir, and is typically the directory in which the Java virtual machine was invoked
     * canRead: Tests whether the application can read the file denoted by this abstract pathname.
     * canWrite: Tests whether the application can modify the file denoted by this abstract pathname.
     * @baseDir
     * @throws IllegalStateException if the file cannot be read, written, or executed
     */
    public DocumentPersistenceManager(File baseDir){
        this.baseDir = baseDir == null ? new File(System.getProperty("user.dir")) : baseDir;
        /*
        if (!(this.baseDir.canRead() || this.baseDir.canWrite())) {
            throw new IllegalStateException();
        }
         */
    }

    /**
     * Base64: Uses "The Base64 Alphabet" as specified in Table 1 of RFC 4648 and RFC 2045 for encoding and decoding operation.
     * Context for serialization that is passed to a custom serializer during invocation of its JsonSerializer.serialize(Object, Type, JsonSerializationContext) method.
     */
    private class ByteAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        /**
         * Serialize: Gson invokes this call-back method during serialization when it encounters a field of the specified type.
         * JasonElement: A class representing an element of Json. It could either be a JsonObject, a JsonArray, a JsonPrimitive or a JsonNull.
         * getEncoder: Returns a Base64.Encoder that encodes using the Basic type base64 encoding scheme.
         * encodeToString: Encodes the specified byte array into a String using the Base64 encoding scheme.
         * @param array
         * @param type
         * @param context
         * @return
         */
        public JsonElement serialize(byte[] array, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.getEncoder().encodeToString(array)); //.withoutPadding()
        }

        /**
         * getDecoder: Returns a Base64.Decoder that decodes using the Basic type base64 encoding scheme.
         * decode: This class implements a decoder for decoding byte data using the Base64 encoding scheme as specified in RFC 4648 and RFC 2045.
         * Why getAsString is used over toString:
         * getAsString: convenience method to get this element as a string value. This method accesses and returns a property of the element, i.e. the value of the element as a java String object.
         * toString: Returns a String representation of this element. This method is the "standard" java toString method, i.e. returns a HUMAN readable representation of the element itself.
         * getAsString() is only defined for primitive types and arrays containing only a single primitive element and throws an exception if called e.g. on an object. toString() will work on all types of JsonElement.
         * In short, both could work, but getAsString is created for this situation.
         * @param element
         * @param type
         * @param context
         * @return
         * @throws JsonParseException
         */
        public byte[] deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            return Base64.getDecoder().decode(element.getAsString());
        }
    }

    /**
     * Try-with-resources: Placing the statement inside the try block. Basically, the concern is that if an exception is called FileReader won't be closed and will be left permanently open for the lifetime of the program. "try-with-resources" guarantees that whether an exception is thrown or not, before control returns from the try-catch block the resource will be closed - https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html.
     * getParentFile: Returns the abstract pathname of this abstract pathname's parent, or null if this pathname does not name a parent directory.
     * mkdirs: Creates the directory named by this abstract pathname. Returns: true if and only if the directory was created; false otherwise
     * FileWriter: Constructs a FileWriter object given a File object.
     * @param uri
     * @param val
     * @throws IllegalArgumentException if toJson fails
     * @throws IOException
     */
    @Override
    public void serialize(URI uri, Document val) throws IOException {
        if (uri == null || val == null) {
            throw new IllegalArgumentException();
        }
        File file = this.getFile(uri);
        file.getParentFile()
                .mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(val, writer);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * getHost: Gets the host name of this URL, if applicable. The format of the host conforms to RFC 2732, i.e. for a literal IPv6 address, this method will return the IPv6 address enclosed in square brackets ('[' and ']'). Returns the host name of this URL as a string.
     * getPath: Gets the path part of this URL. Returns: the path part of this URL, or an empty string if one does not exist
     * separatorChar: The system-dependent default name-separator character. This field is initialized to contain the first character of the value of the system property file.separator. On UNIX systems the value of this field is '/'; on Microsoft Windows systems it is '\\'.
     * @param uri
     * @return
     */
    private File getFile(URI uri) {
        String hostName = uri.getHost();
        String pathName = uri.getPath();
        if (pathName == null) {
            pathName = "";
        }
        if (hostName == null) {
            hostName = "";
        }
        File file = new File(this.baseDir.toString() + File.separatorChar + pathName + hostName + ".json");
        return file;
    }

    /**
     * FileReader: Convenience class for reading character files. The constructors of this class assume that the default character encoding and the default byte-buffer size are appropriate. To specify these values yourself, construct an InputStreamReader on a FileInputStream.
     * fromJson: This method deserializes the Json read from the specified parse tree into an object of the specified type. It is not suitable to use if the specified class is a generic type since it will not have the generic type information because of the Type Erasure feature of Java. Therefore, this method should not be used if the desired type is a generic type. Note that this method works fine if the any of the fields of the specified object are generics, just the object itself should not be a generic type.
     * file.delete: Deletes the file or directory denoted by this abstract pathname.
     * close: If close is not called then the file will remain open forever
     * @param uri
     * @return
     * @throws IllegalArgumentException - if passed a uri that does not exists (could also just return null)
     * @throws IOException
     */
    @Override
    public Document deserialize(URI uri) throws IOException {
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        File file = this.getFile(uri);
        if (!file.exists()) {
            return null;
        }
        try (FileReader reader = new FileReader(file)) {
            Document doc = gson.fromJson(reader, DocumentImpl.class);
            reader.close();
            this.delete(uri); //Files.delete(file.toPath());
            return doc;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * file.delete: Deletes the file or directory denoted by this abstract pathname.
     * endsWith: Tests if this path ends with the given path.
     * delete the file stored on disk that corresponds to the given key
     * @param uri
     * @return true or false to indicate if deletion occured or not
     * @throws IOException
     */
    @Override
    public boolean delete(URI uri) throws IOException {
        if (uri == null) {
            return false;
        }
        File file = this.getFile(uri);
        if (!file.delete()) {
            return false;
        }
        file = file.getParentFile();
        while (file.getPath().endsWith(this.baseDir.getPath())) {
            file.delete();
            file = file.getParentFile();
        }
        return true;
    }
}