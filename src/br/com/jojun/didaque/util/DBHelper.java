package br.com.jojun.didaque.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.jojun.didaque.DidaqueApplication;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION;
//	private static final String TAG = DBHelper.class.getSimpleName();
	private static DBHelper instance;
	private static String dbName;
	public static final String DB_NAME = "didaque-db.sqlite";
	public static final String DB_NAME_ASSETS = "didaque-db.sqlite";
	
    public DBHelper(Context context) {
        super(context, dbName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	copyDatabase();
    }

    public static void copyDatabase(){
    	File dbFile = DidaqueApplication.getContext().getDatabasePath(DB_NAME);
		InputStream is;
		try {
			is = DidaqueApplication.getContext().getAssets().open(DB_NAME_ASSETS);

			OutputStream os = new FileOutputStream(dbFile);

			byte[] buffer = new byte[1024];
			while (is.read(buffer) > 0) {
				os.write(buffer);
			}

			os.flush();
			os.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("DBHELPER", db.getVersion()+" - old "+oldVersion+" - new "+newVersion );
		if(newVersion == 2) {
			db.execSQL("DROP TABLE IF EXISTS TextoCatequese");
			db.execSQL("ALTER TABLE Apostila ADD COLUMN idioma TEXT");
			db.execSQL("ALTER TABLE TextoAleatorio ADD COLUMN idioma TEXT");
			
			db.execSQL("UPDATE Apostila SET idioma = 'PT'");
			db.execSQL("UPDATE TextoAleatorio SET idioma = 'PT'");
	
			/*
			 * INSERT proposito eterno espanhol
			 * 
			 */
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (1,'El Propósito Eterno', 'El Propósito de Dios al crear al hombre', '<i>¿Cuál era el propósito de Dios cuando creó al hombre?</i><br /><b>El Propósito de Dios al crear al hombre</b><br /><br />\"Entonces dijo Dios: Hagamos al hombre a nuestra imagen, con- forme a nuestra semejanza.\" Gn 1.26', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (2,'El Propósito Eterno', '¿Qué sucedió cuando el hombre pecó?', '<i>¿Qué pasó cuando el hombre pecó?</i><br /><b>El hombre se volvió inútil para el propósito de Dios</b><br /><hr /><br /><i>¿Dios desistió de su propósito por causa del pecado?</i><br /><b>No. Dios no desistió de su propósito.</b><br /><br />\"Todos se desviaron, a una se hicieron inútiles.\" Rm 3.12', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (3,'El Propósito Eterno', '¿Qué es lo que Dios hizo para realizar su propósito', '<i>Si el hombre se volvió inútil, ¿Qué esperanza tiene Dios de realizar Su propósito?</i><br /><b>El nos da una nueva vida en Cristo. La esperanza de Dios es la vida de Cristo en nosotros.</b><br /><br />\"De modo que si alguno está en Cristo, nueva criatura es; las cosas viejas pasaron; he aquí todas son hechas nuevas.\" 2Co 5:17 <br /><hr /><br />\"Cristo en vosotros, la esperanza de gloria.\" Col 1:2', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (4,'El Propósito Eterno', '¿Cuál es el propósito de Dios hoy?', '<i>¿Cuál es el propósito de Dios hoy?</i><br /><b>Dios quiere una familia de muchos hijos semejantes a Jesús.</b><br /> <i>Por que uma família?</i><br /><b>Porque Deus quer unidade.</b><br />\"Y sabemos que a los que aman a Dios, todas las cosas les ayudan a bien, esto es, a los que conforme a su propósito son llamados. Porque a los que antes conoció, también los predestinó para que fuesen hechos conformes a la im- agen de su Hijo, para que él sea el primogénito entre muchos her- manos.\" Rm 8:28-29<br /><br /><i>¿Por qué una familia?</i><b>Porque Dios quiere unidad.</b><br /><hr /><br /><i>¿Por qué muchos hijos?</i><b>Porque Dios quiere cantidad.</b><br /><hr /><br /><i>¿Por qué semejantes a Jesús?</i><b>Porque Dios quiere calidad.</b><br /><br />\"Hasta que todos lleguemos a la unidad de la fe y del conocimiento del Hijo de Dios, a un varón perfecto, a la medida de la estatura de la plenitud de Cristo.\" Ef 4:13', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (5,'El Propósito Eterno', 'Debemos ser como Jesús', '<i>¿El propósito de Dios no es la salvación del hombre?</i><br /><b>No, la salvación es el medio para alcanzar el propósito. Su propósito es que seamos semejantes a Jesús.</b><br />\"El que dice que permanece en él, debe andar como él anduvo.\" 1Jn 2:6<br /><hr /><br /><i>¿En qué debemos ser como Jesús?</i><br /><b>Ser mansos y humildes como Jesús.</b><br />\"Llevad mi yugo sobre vosotros, y aprended de mí, que soy manso y humilde de corazón; y hallaréis descanso para vuestras almas.\" Mt 11:29<br /><hr /><br /><b>Ser santos como Jesús.</b><br />\"Sino como aquel que os llamó es santo, sed también vosotros santos en toda vuestra manera de vivir.\" 1Pe 1:15<br /><hr /><br /><b>Servir como Jesús.</b><br />\"Pues si yo, el Señor y el Maestro, he lavado vuestros pies, vosotros también debéis lavaros los pies los unos a los otros.\" Jn 13:14<br /><hr /><br /><b>Predicar al mundo como Jesús.</b><br />\"Como tú me enviaste al mundo, así yo los he enviado al mundo.\" Jn 17:18<br /><hr /><br /><b>Perdonar como Jesús.</b><br />\"De la manera que Cristo os perdonó, así también hacedlo vosotros.\" Col 3:13<br /><hr /><br /><b>Amar como Jesús.</b><br />\"Un mandamiento nuevo os doy: Que os améis unos a otros; como yo os he amado, que también os améis unos a otros.\" Jn 13:34<br />', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (6,'El Propósito Eterno', '¿Quiénes son sacerdotes?', '<i>¿Quiénes son los sacerdotes de la Iglesia?</i><br /><b>Todos los santos son sacerdotes</b><br /><br />\"Mas vosotros sois linaje es- cogido, real sacerdocio, nación santa, pueblo adquirido por Dios, para que anunciéis las virtudes de aquel que os llamó de las tinieblas a su luz admirable\" 1Pe 2:9', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (7,'El Propósito Eterno', '¿Quién ediﬁca la Iglesia?', '<i>¿Quién ediﬁca el cuerpo de Cristo?</i><br /><b>El cuerpo de Cristo ediﬁca el cuerpo de Cristo</b><br /><br />\"Y él mismo constituyó a unos, apóstoles; a otros, profetas; a otros, evangelistas; a otros, pas- tores y maestros, a ﬁn de perfec- cionar a los santos para la obra del ministerio, para la ediﬁcación del cuerpo de Cristo.\" Ef 4:11-12', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (8,'El Propósito Eterno', 'El ministerio de ser testigos (1ª parte)', '<i>¿Cómo iniciamos el servicio de hacer discípulos?</i><br /><b>Siendo testigos y proclamadores.</b><br /><br />\"Pero recibiréis poder, cuando haya venido sobre vosotros el Espíritu Santo, y me seréis testi- gos en Jerusalén, en toda Judea, en Samaria, y hasta lo último de la tierra.\" Hch 1:8', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (9,'El Propósito Eterno', 'El ministerio de ser testigos (2ª parte)', '<i>¿Cómo iniciamos el servicio de hacer discípulos?</i><br /><b>Siendo testigos y proclamadores.</b><br /><br />\"Pero recibiréis poder, cuando haya venido sobre vosotros el Espíritu Santo, y me seréis testi- gos en Jerusalén, en toda Judea, en Samaria, y hasta lo último de la tierra.\" Hch 1:8', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (10,'El Propósito Eterno', 'El ministerio de las coyunturas y ligamentos', '<i>¿Qué son las coyunturas y ligamentos del cuerpo de Cristo?</i><br /><b>Coyunturas y ligamentos del cuerpo de Cristo son relaciones fuertes y resistentes entre sus miembros.</b><br /><hr /><br /><i>¿Para qué sirven las coyunturas y ligamentos?</i><br /><b>Para unir, alimentar y ediﬁcar el cuerpo de Cristo.</b><br /><br />\"Sino que siguiendo la ver- dad en amor, crezcamos en todo en aquel que es la cabeza, esto es, Cristo, de quien todo el cuerpo, bien concertado y unido entre sí por todas las coyunturas que se ayudan mutuamente, según la actividad propia de cada miembro, recibe su crecimiento para ir ediﬁcándose en amor.\" Ef 4:15-16', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (11,'El Propósito Eterno', 'Coyunturas y ligamentos de discipulado (1ª parte)', '<i>¿Cuál es la función del discipulador?</i><br /><b>Ensen˜ar a guardar todas las cosas que Jesús ordenó.</b><br /><br />\"Por tanto, id, y haced discípulos a todas las naciones, bautizándolos en el nombre del Padre, y del Hijo, y del Espíritu Santo; enseñándoles que guarden todas las cosas que os he mandado; y he aquí yo estoy con vosotros todos los días, hasta el ﬁn del mundo. Amén.\" Mt 28:19-20', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (12,'El Propósito Eterno', 'Coyunturas y ligamentos de discipulado (2ª parte)', '<i>¿Qué es necesario para ser discipulado?</i><br /><b>Ser manso, humilde y sumiso.</b><br /><br />\"Someteos unos a otros en el temor de Dios.\" Ef 5:21', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (13,'El Propósito Eterno', 'Coyunturas y ligamentos de compañerismo (1ª parte)', '<i>¿Por qué el discipulado y el compañerismo son tan importantes?</i><br /><b>Porque unen el cuerpo por coyunturas y ligamentos.</b><br /><hr /><br /><i>¿Cuales son las principales actitudes del compañerismo?</i><br /><b>Amor, sumisión, transparencia y perdón.</b><br /><br />\"Un mandamiento nuevo os doy: Que os améis unos a otros; como yo os he amado, que también os améis unos a otros.\" Jn 13:3', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (14,'El Propósito Eterno', 'Coyunturas y ligamentos de compañerismo (2ª parte)', '<i>¿Cuáles son las principales actividades del compañerismo?</i><br /><b>Orar, aconsejar, servir y hacer discípulos.</b><br /><hr /><br /><i>¿Cuál es el fruto de esto?</i><br /><b>La ediﬁcación del cuerpo en amor.</b><br /><br />\"La palabra de Cristo more en abundancia en vosotros, enseñándoos y exhortándoos unos a otros en toda sabiduría\" Col 3:16', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (15,'El Propósito Eterno', 'La necesidad de dar Fruto', '<i>¿Qué es lo que el agricultor exige del ramo?</i><br /><b>Todo ramo debe dar fruto.</b><br /><hr /><br /><i>¿Cuál es el fruto que debe dar el ramo?</i><br /><b>La multiplicación de la vida de Cristo.</b><br /><br />\"No me elegisteis vosotros a mí, sino que yo os elegí a vosotros, y os he puesto para que vayaís y llevéis fruto, y vuestro fruto permanezca.\" Jn 15:16', 1, 'ES')");
			db.execSQL("INSERT INTO Apostila (numeroLicao,tituloApostila,tituloLicao,catequese,numeroApostila,idioma)  VALUES (16,'El Propósito Eterno', 'El trabajo en las casas', '<i>¿Cuál es el motivo del encuentro del grupo casero?</i><br /><b>La obra del grupo casero es el desarrollo del servicio de los santos.</b>', 1, 'ES')");
			
			
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('\"Esta empero es la vida eterna: que te conozcan el solo Dios verdadero, y á Jesucristo, al cual has enviado.\" Ju 17.3.', 'La comunión con Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('El propósito de Dios no comenzó con la cáıda del hombre. Es algo que ya estaba en su corazón antes de la fundación del mundo.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('\"según nos escogió en él antes de la fundación del mundo, para que fuésemos santos y sin mancha delante de él.\" Ef 1:4.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('Cuando Dios creó al hombre, él quería una familia de hombres semejantes a él.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('Con el pecado, el hombre se volvió una criatura inútil para el propósito de Dios.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('\"Por lo cual, queriendo Dios mostrar más abundantemente a los herederos de la promesa la inmutabilidad de su consejo, interpuso juramento.\" Heb 6:17', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('Dios no desistió de Su Propósito por causa del pecado. él es inmutable.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('Dios nos da una nueva vida en Cristo.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('\"Cristo en vosotros, la esperanza de gloria.\" Col 1:27', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('Dios quiere una familia de muchos hijos semejantes a Jesús.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('Cuando comprendemos y abrazamos el propósito de Dios, el se convierte en nuestro llamado y en nuestra vocación.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('La salvación no es la meta, es el medio para alcanzar el propósito.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('Somos una nación de sacerdotes. Todos los discípulos son siervos de Dios.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('El cuerpo de Cristo ediﬁca el cuerpo de Cristo.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('Somos sólo cooperadores.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('El testimonio personal es la cosa más simple y concreta que tenemos para contar. Es irrefutable.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('Predicar el Evangelio del Reino es hablar de Jesús y de la Puerta del Reino.', 'El Propósito Eterno de Dios', 'ES')");
			db.execSQL("INSERT INTO TextoAleatorio (texto,licao,idioma)  VALUES ('Todos los miembros del cuerpo deben estar unidos y bien concertados entre sí por todas las coyunturas y ligamentos.', 'El Propósito Eterno de Dios', 'ES')");

		}
		
//		copyDatabase();
	}
	
	public static DBHelper getInstance(Context context){
		if(instance == null)
			instance = new DBHelper(context);
		
		return instance;
	}
	
	public static DBHelper getInstance(){
		if(instance == null) {
	        ApplicationInfo ai;
			try {
				ai = DidaqueApplication.getContext().getPackageManager().getApplicationInfo(DidaqueApplication.getContext().getPackageName(), PackageManager.GET_META_DATA);
		        Bundle bundle = ai.metaData;
		        dbName = bundle.getString("DATABASE_NAME"); 
		        DATABASE_VERSION = bundle.getInt("DATABASE_VERSION");
			} catch (NameNotFoundException e) {
				dbName = "database.sqlite";
			}
			instance = new DBHelper(DidaqueApplication.getContext());
		}
		
		return instance;
	}

}
