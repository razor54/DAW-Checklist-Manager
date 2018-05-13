
export default function (requestBody){

  if(!requestBody.user)
    return new UserModel('','','') //user with empty elements

  return new UserModel(
    requestBody.user.id,
    requestBody.user.name,
    requestBody._links.self.href
  )
}





class UserModel{

  constructor (id,name,selfLink){
    this.id = id
    this.name = name
    this.selfLink = selfLink
  }
}


/*
{
    "user": {
        "id": "bnVubzE6MTIzNDU=",
        "name": "nuno1"
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/listing/user/bnVubzE6MTIzNDU="
        },
        "checklists": {
            "href": "http://localhost:8080/listing/checklists"
        },
        "templates": {
            "href": "http://localhost:8080/listing/templates"
        }
    }
}
 */