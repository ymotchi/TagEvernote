package code.snippet

import net.liftweb.http.SessionVar
import com.evernote.edam.notestore.NoteStore.Client

object SessionAuthToken extends SessionVar[Option[String]](None)
object SessionNoteStore extends SessionVar[Option[Client]](None)
