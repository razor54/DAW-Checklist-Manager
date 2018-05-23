

export default function (requestBody){

  if(!requestBody._embedded)
    return new ListModel([],'') //empty list

  return new ListModel(
    requestBody._embedded.templateItemResourceList,
    requestBody._links.self.href
  )

}


class ListModel{

  constructor (items,selfLink){
    this.items = items.map(item => {
      return {
        id: item.templateItem.id,
        name: item.templateItem.name,
        selfLink: item._links.self.href
      }
    })
    this.selfLink = selfLink
  }
}



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
