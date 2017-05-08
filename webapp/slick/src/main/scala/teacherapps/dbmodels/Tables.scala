package teacherapps.dbmodels

// AUTO-GENERATED Slick data model [2017-03-19T21:46:36.532+01:00[Europe/Paris]]

/** Stand-alone Slick data model for immediate use */
object Tables {
  val profile = teacherapps.dbmodels.TeacherAppsDriver
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Classes.schema ++ Pupils.schema ++ Teachers.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Classes
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text)
   *  @param teacherId Database column teacher_id SqlType(int4)
   *  @param disabled Database column disabled SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class ClassesRow(id: Int, name: String, teacherId: Int, disabled: Boolean = false, createdAt: java.time.ZonedDateTime, updatedAt: java.time.ZonedDateTime)
  /** GetResult implicit for fetching ClassesRow objects using plain SQL queries */
  implicit def GetResultClassesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Boolean], e3: GR[java.time.ZonedDateTime]): GR[ClassesRow] = GR{
    prs => import prs._
    ClassesRow.tupled((<<[Int], <<[String], <<[Int], <<[Boolean], <<[java.time.ZonedDateTime], <<[java.time.ZonedDateTime]))
  }
  /** Table description of table classes. Objects of this class serve as prototypes for rows in queries. */
  class Classes(_tableTag: Tag) extends Table[ClassesRow](_tableTag, "classes") {
    def * = (id, name, teacherId, disabled, createdAt, updatedAt) <> (ClassesRow.tupled, ClassesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(teacherId), Rep.Some(disabled), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> ClassesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column teacher_id SqlType(int4) */
    val teacherId: Rep[Int] = column[Int]("teacher_id")
    /** Database column disabled SqlType(bool), Default(false) */
    val disabled: Rep[Boolean] = column[Boolean]("disabled", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.time.ZonedDateTime] = column[java.time.ZonedDateTime]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.time.ZonedDateTime] = column[java.time.ZonedDateTime]("updated_at")

    /** Foreign key referencing Teachers (database name classes_teacher_id_fkey) */
    lazy val teachersFk = foreignKey("classes_teacher_id_fkey", teacherId, Teachers)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Classes */
  lazy val Classes = new TableQuery(tag => new Classes(tag))

  /** Entity class storing rows of table Pupils
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param firstName Database column first_name SqlType(text)
   *  @param lastName Database column last_name SqlType(text)
   *  @param login Database column login SqlType(text)
   *  @param password Database column password SqlType(text)
   *  @param classId Database column class_id SqlType(int4)
   *  @param disabled Database column disabled SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class PupilsRow(id: Int, firstName: String, lastName: String, login: String, password: String, classId: Int, disabled: Boolean = false, createdAt: java.time.ZonedDateTime, updatedAt: java.time.ZonedDateTime)
  /** GetResult implicit for fetching PupilsRow objects using plain SQL queries */
  implicit def GetResultPupilsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Boolean], e3: GR[java.time.ZonedDateTime]): GR[PupilsRow] = GR{
    prs => import prs._
    PupilsRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[Int], <<[Boolean], <<[java.time.ZonedDateTime], <<[java.time.ZonedDateTime]))
  }
  /** Table description of table pupils. Objects of this class serve as prototypes for rows in queries. */
  class Pupils(_tableTag: Tag) extends Table[PupilsRow](_tableTag, "pupils") {
    def * = (id, firstName, lastName, login, password, classId, disabled, createdAt, updatedAt) <> (PupilsRow.tupled, PupilsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(firstName), Rep.Some(lastName), Rep.Some(login), Rep.Some(password), Rep.Some(classId), Rep.Some(disabled), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> PupilsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column first_name SqlType(text) */
    val firstName: Rep[String] = column[String]("first_name")
    /** Database column last_name SqlType(text) */
    val lastName: Rep[String] = column[String]("last_name")
    /** Database column login SqlType(text) */
    val login: Rep[String] = column[String]("login")
    /** Database column password SqlType(text) */
    val password: Rep[String] = column[String]("password")
    /** Database column class_id SqlType(int4) */
    val classId: Rep[Int] = column[Int]("class_id")
    /** Database column disabled SqlType(bool), Default(false) */
    val disabled: Rep[Boolean] = column[Boolean]("disabled", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.time.ZonedDateTime] = column[java.time.ZonedDateTime]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.time.ZonedDateTime] = column[java.time.ZonedDateTime]("updated_at")

    /** Foreign key referencing Classes (database name pupils_class_id_fkey) */
    lazy val classesFk = foreignKey("pupils_class_id_fkey", classId, Classes)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)

    /** Uniqueness Index over (login) (database name pupils_login_key) */
    val index1 = index("pupils_login_key", login, unique=true)
  }
  /** Collection-like TableQuery object for table Pupils */
  lazy val Pupils = new TableQuery(tag => new Pupils(tag))

  /** Entity class storing rows of table Teachers
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param firstName Database column first_name SqlType(text)
   *  @param lastName Database column last_name SqlType(text)
   *  @param login Database column login SqlType(text)
   *  @param password Database column password SqlType(text)
   *  @param email Database column email SqlType(text)
   *  @param disabled Database column disabled SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class TeachersRow(id: Int, firstName: String, lastName: String, login: String, password: String, email: String, disabled: Boolean = false, createdAt: java.time.ZonedDateTime, updatedAt: java.time.ZonedDateTime)
  /** GetResult implicit for fetching TeachersRow objects using plain SQL queries */
  implicit def GetResultTeachersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Boolean], e3: GR[java.time.ZonedDateTime]): GR[TeachersRow] = GR{
    prs => import prs._
    TeachersRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[Boolean], <<[java.time.ZonedDateTime], <<[java.time.ZonedDateTime]))
  }
  /** Table description of table teachers. Objects of this class serve as prototypes for rows in queries. */
  class Teachers(_tableTag: Tag) extends Table[TeachersRow](_tableTag, "teachers") {
    def * = (id, firstName, lastName, login, password, email, disabled, createdAt, updatedAt) <> (TeachersRow.tupled, TeachersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(firstName), Rep.Some(lastName), Rep.Some(login), Rep.Some(password), Rep.Some(email), Rep.Some(disabled), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> TeachersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column first_name SqlType(text) */
    val firstName: Rep[String] = column[String]("first_name")
    /** Database column last_name SqlType(text) */
    val lastName: Rep[String] = column[String]("last_name")
    /** Database column login SqlType(text) */
    val login: Rep[String] = column[String]("login")
    /** Database column password SqlType(text) */
    val password: Rep[String] = column[String]("password")
    /** Database column email SqlType(text) */
    val email: Rep[String] = column[String]("email")
    /** Database column disabled SqlType(bool), Default(false) */
    val disabled: Rep[Boolean] = column[Boolean]("disabled", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.time.ZonedDateTime] = column[java.time.ZonedDateTime]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.time.ZonedDateTime] = column[java.time.ZonedDateTime]("updated_at")

    /** Uniqueness Index over (email) (database name teachers_email_key) */
    val index1 = index("teachers_email_key", email, unique=true)
    /** Uniqueness Index over (login) (database name teachers_login_key) */
    val index2 = index("teachers_login_key", login, unique=true)
  }
  /** Collection-like TableQuery object for table Teachers */
  lazy val Teachers = new TableQuery(tag => new Teachers(tag))
}
