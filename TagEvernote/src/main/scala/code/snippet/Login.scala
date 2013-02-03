package code.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.SHtml
import com.evernote.edam.userstore.Constants
import com.evernote.thrift.transport.THttpClient
import com.evernote.thrift.protocol.TBinaryProtocol
import com.evernote.edam.userstore.UserStore
import com.evernote.edam.notestore.NoteStore
import net.liftweb.http.S

object Login {

  val evernoteHost = "sandbox.evernote.com"
  val userStoreUrl = "https://" + evernoteHost + "/edam/user"
  val userAgent = "Evernote/EDAMDemo (Java) " + Constants.EDAM_VERSION_MAJOR + "." + Constants.EDAM_VERSION_MINOR
  val userStoreTrans = new THttpClient(userStoreUrl)
  userStoreTrans.setCustomHeader("User-Agent", userAgent)
  val userStoreProt = new TBinaryProtocol(userStoreTrans)
  val userStore = new UserStore.Client(userStoreProt, userStoreProt)

  def login = {
    var authToken = ""

      "name=authToken" #> SHtml.onSubmit(authToken = _) &
      "type=submit" #> SHtml.onSubmitUnit { () =>
          val noteStoreTrans = new THttpClient(userStore.getNoteStoreUrl(authToken))
          noteStoreTrans.setCustomHeader("User-Agent", userAgent)
          val noteStoreProt = new TBinaryProtocol(noteStoreTrans)

          SessionAuthToken.set(Some(authToken))
          SessionNoteStore.set(Some(new NoteStore.Client(noteStoreProt, noteStoreProt)))

          S.redirectTo("index")
      }
  }
}