import Notifier.NotifyRead;
import Notifier.NotifyThread;
import WriteReader.Reader;
import calculate.KochData;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * Created by Gebruiker on 4-12-2017.
 */
public class Read {

    public static void main(String[] args) {

        //new Thread(new NotifyThread()).start();

        final WatchService watcher;
        // Voorbeelden van interessante locaties
        // Path dir = Paths.get("D:\\");
        Path dir = Paths.get("C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files");
        WatchKey key;

        try {
            watcher = FileSystems.getDefault().newWatchService();
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            while (true) {
                key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;

                    Path filename = ev.context();
                    Path child = dir.resolve(filename);

                    WatchEvent.Kind kind = ev.kind();
                    if (kind == ENTRY_CREATE) {
                        System.out.println(child + " created");
                    }
                    if (kind == ENTRY_DELETE) {
                        System.out.println(child + " deleted");
                    }
                    if (kind == ENTRY_MODIFY) {
                        System.out.println(child + " modified");
                        NotifyRead read = new NotifyRead();
                        read.readMessage();
                    }
                }
                key.reset();
            }

        } catch (IOException | InterruptedException ex) {
            System.out.println("You fucked up son!");
        }

//        NotifyRead read = new NotifyRead();
//        read.readMessage();

    }

//    beide applicaties tegelijkertijd draaien met file mapping
//    en dat locking gebruikt wordt voor de synchronisatie (geen directory notification)

}
