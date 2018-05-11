


export default function listDTO(requestBody){

  return new ListModel(
    requestBody._embedded.checklistItemResourceList,
    requestBody._links.self.href
  )
}


class ListModel{

  constructor (items,selfLink){
    this.items = items
    this.selfLink = selfLink
  }
}


// EXAMPLE OF LIST ITEMS
/*
{
  "_embedded": {
  "checklistItemResourceList": [
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
  ]
},
  "_links": {
  "self": {
    "href": "http://localhost:8080/listing/checklist/1/items"
  }
}
}*/
