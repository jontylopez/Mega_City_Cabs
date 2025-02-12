package DateAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SqlDateAdapter extends TypeAdapter<Date> {
 

    public final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(dateFormat.format(value)); // ✅ Convert Date to String
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        try {
            String dateStr = in.nextString();
            return new Date(dateFormat.parse(dateStr).getTime()); // ✅ Convert String to Date
        } catch (ParseException e) {
            throw new IOException("Error parsing date: " + e.getMessage());
        }
    }
}
