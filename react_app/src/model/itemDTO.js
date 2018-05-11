
export default function listDTO(requestBody){

  return new ItemModel(
    requestBody.checklistItem.id,
    requestBody.checklistItem.name,
    requestBody.checklistItem.description,
    requestBody.checklistItem.state,
    requestBody.checklistItem.list_id,
    requestBody._links.parent.href,
    requestBody._links.self.href
  )
}





class ItemModel{

  constructor (id,name,description,checkState,listId,selfLink, parentLink){
    this.id = id
    this.name = name
    this.description = description
    this.checkState = checkState
    this.listId = listId
    this.selfLink = selfLink
    this.parentLin = parentLink
  }
}


/*
{
    "checklistItem": {
        "id": 1,
        "name": "mychecklistitem",
        "state": "false",
        "description": "this is my checklist item",
        "list_id": 1
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/listing/checklist/item/1"
        },
        "parent": {
            "href": "http://localhost:8080/listing/checklist/1"
        }
    }
}
 */