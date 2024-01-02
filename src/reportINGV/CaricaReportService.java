package reportINGV;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CaricaReportService extends Service<ObservableList<INGEvent>> {
    private final URL url;
    private final Integer limit;

    public CaricaReportService(URL url, String limit) {
        this.url = url;
        this.limit = Integer.parseInt(limit);
    }

    public URL getURL() {
        return url;
    }

    public Integer getLimit() {
        return limit;
    }

    @Override
    protected Task<ObservableList<INGEvent>> createTask() {
        return new Task<ObservableList<INGEvent>>() {
            @Override
            protected ObservableList<INGEvent> call() throws Exception {
                ObservableList<INGEvent> list = FXCollections.observableArrayList();

                try (Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(url.openStream())))) {
                    sc.useDelimiter("[|\n]");
                    sc.useLocale(Locale.US);

                    updateMessage("Loading starts soon..");
                    Thread.sleep(1000);

                    updateMessage("Loading...");
                    if (sc.hasNextLine())
                        sc.nextLine();

                    int i = 0;
                    while (sc.hasNext()) {
                        INGEvent ev = new INGEvent();
                        ev.setEventID(sc.next());
                        ev.setTime(LocalDateTime.parse(sc.next(),
                                DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        ev.setLatitude(sc.nextDouble());
                        ev.setLongitude(sc.nextDouble());
                        ev.setDepthKm(sc.nextFloat());
                        ev.setAuthor(sc.next());
                        ev.setCatalog(sc.next());
                        ev.setContributor(sc.next());
                        ev.setContributorID(sc.next());
                        ev.setMagType(sc.next());
                        ev.setMagnitude(sc.nextFloat());
                        ev.setMagAuthor(sc.next());
                        ev.setEventLocationName(sc.next());
                        ev.setEventType(sc.next());

                        list.add(ev);

                        updateProgress(i++, limit);

                        updateValue(list);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    updateMessage("Parsing error");

                    return list;
                }
                updateMessage("Completed");
                return list;
            }
        };
    }
}
