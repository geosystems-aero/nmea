package aero.geosystems.decoder.nmea;

/**
 * Created by IntelliJ IDEA.
 * User: aimozg
 * Date: 21.04.2014
 * Time: 17:56
 */
public class NmeaGga extends AbstractNmea {

	public final NmeaDatetimeField utcOfPosition = new NmeaDatetimeField("HHmmss.cc");
	public final NmeaLatLonField lat = new NmeaLatLonField('N', 'S', "%02d%07.4f");
	public final NmeaLatLonField lon = new NmeaLatLonField('E', 'W', "%03d%07.4f");
	public final NmeaFormatField<Integer> quality = new NmeaFormatField<>("%d");
	public final NmeaFormatField<Integer> numSats = new NmeaFormatField<>("%02d");
	public final NmeaFormatField<Double> hdop = new NmeaFormatField<>("%.1f");
	public final NmeaFormatField<Double> antennaAltitude = new NmeaFormatField<>("%.1f");
	public final NmeaConstField unitsOfAntennaAltitude = new NmeaConstField("M");
	public final NmeaFormatField<Double> geoidalSeparation = new NmeaFormatField<>("%.1f");
	public final NmeaConstField unitsOfGeoidalSeparation = new NmeaConstField("M");
	public final NmeaFormatField<Double> ageOfDgpsData = new NmeaFormatField<>("%.1f");
	public final NmeaFormatField<Integer> diffStationId = new NmeaFormatField<>("%d");

	public NmeaGga(String talker) {
		super(talker, "GGA");
	}

	public NmeaGga() {
		super("GP", "GGA");
	}
}
