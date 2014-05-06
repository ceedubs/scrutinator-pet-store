package net.ceedubs.scrutinator.petstore

import org.scalatra._
import scalate.ScalateSupport

class PetStoreApi extends ScrutinatorPetStoreStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }
  
}
