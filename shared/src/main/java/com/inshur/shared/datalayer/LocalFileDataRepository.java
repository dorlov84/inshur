package com.inshur.shared.datalayer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LocalFileDataRepository<T> implements DataRepository<T> {

    private final Class<T> classType;
    private final String dbDirectory;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    public LocalFileDataRepository(Class<T> clazz) {
        dbDirectory = System.getProperty("user.home");
        this.classType = clazz;
    }

    @Override
    public void save(T item) {
        Lock writeLock = rwLock.writeLock();

        FileWriter fileWriter = null;
        try {
            writeLock.lock();
            File storageFile = getOrCreateStorageFile(classType);

            fileWriter = new FileWriter(storageFile, true);

            ObjectMapper mapper = new ObjectMapper();
            fileWriter.write(mapper.writeValueAsString(item));
            fileWriter.write(System.lineSeparator());
        }
        catch (IOException io) {
            // Need to process correctly
        } finally {
            writeLock.unlock();
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    //
                }
            }
        }
    }

    @Override
    public List<T> findAll() {
        Lock readLock = rwLock.readLock();

        List<T> result = new LinkedList<>();
        BufferedReader fileReader = null;
        try {
            readLock.lock();

            File storageFile = getOrCreateStorageFile(classType);

            ObjectMapper objectMapper = new ObjectMapper();
            fileReader = new BufferedReader(new FileReader(storageFile));

            String line;
            while ((line = fileReader.readLine()) != null) {
                result.add(objectMapper.readValue(line, classType));
            }

        } catch (IOException e) {
            System.out.println(e);
            // Need to process correctly
        } finally {
            readLock.unlock();
            if (fileReader !=  null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    //
                }
            }
        }

        return result;
    }

    private File getOrCreateStorageFile(Class<T> type) throws IOException {

        File store = new File(dbDirectory + "/" + type.getSimpleName() + ".ldb");
        if (!store.exists()) {
            store.createNewFile();
        }

        return store;
    }

}
