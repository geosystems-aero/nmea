package aero.geosystems.decoder.nmea;

import aero.geosystems.gnss.Datetime;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: aimozg
 * Date: 21.04.2014
 * Time: 17:40
 */
public class AbstractNmea {
	protected final ArrayList<NmeaField> _fields = new ArrayList<>();
	public final String talker;
	public final String message;

	public abstract class NmeaField {
		public abstract String toNmeaString();

		public NmeaField() {
			_fields.add(this);
		}
	}

	public class NmeaConstField extends NmeaField {

		public final String constString;

		public NmeaConstField(String constString) {
			this.constString = constString;
		}

		@Override
		public String toNmeaString() {
			return constString;
		}
	}

	public abstract class NmeaValueField<T> extends NmeaField {
		protected T value;

		public T get() {
			return value;
		}

		public void set(T value) {
			this.value = value;
		}

		@Override
		public String toNmeaString() {
			if (value == null) return "";
			else return valueToString(value);
		}

		protected abstract String valueToString(T value);
	}

	public class NmeaFormatField<T> extends NmeaValueField<T> {

		private final String formatString;

		public NmeaFormatField(String formatString) {
			this.formatString = formatString;
		}

		@Override
		public String valueToString(T value) {
			return String.format(Locale.ENGLISH, formatString, value);
		}
	}

	public class NmeaDatetimeField extends NmeaValueField<Datetime> {

		private final String formatString;

		public NmeaDatetimeField(String formatString) {
			this.formatString = formatString;
		}

		@Override
		protected String valueToString(Datetime value) {
			return value.format(formatString);
		}
	}

	public class NmeaLatLonField extends NmeaValueField<Double> {
		private final char posChar;
		private final char negChar;
		private final String numFormat;

		public NmeaLatLonField(char posChar, char negChar, String numFormat) {
			this.posChar = posChar;
			this.negChar = negChar;
			this.numFormat = numFormat;
		}

		@Override
		public String toNmeaString() {
			if (value == null) return ",";
			return valueToString(get());
		}

		@Override
		protected String valueToString(Double value) {
			boolean neg = value < 0;
			if (neg) value = -value;
			double min = (value - value.intValue()) * 60;
			return String.format(Locale.ENGLISH, numFormat + ",%s", value.intValue(), min, neg ? negChar : posChar);
		}
	}

	public AbstractNmea(String talker, String message) {
		this.talker = talker;
		this.message = message;
	}

	public String toNmeaString() {
		StringBuilder s = new StringBuilder();
		s.append(talker);
		s.append(message);
		for (NmeaField field : _fields) {
			s.append(',');
			s.append(field.toNmeaString());
		}
		int checksum = 0;
		for (int i = 0; i < s.length(); i++) {
			checksum ^= s.charAt(i);
		}
		checksum &= 0xff;
		s.insert(0, "$").append("*").append(String.format("%02x", checksum).toUpperCase());
		return s.toString();
	}
}
