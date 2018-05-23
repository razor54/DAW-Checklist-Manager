
export default function (requestBody){

  if(!requestBody.templateItem)
    return new ItemModel('','','',false,'','','') //item with empty elements

  return new ItemModel(
    requestBody.templateItem.id,
    requestBody.templateItem.name,
    requestBody.templateItem.description,
    requestBody.templateItem.template_id,
    requestBody._links.parent.href,
    requestBody._links.self.href
  )
}





class ItemModel{

  constructor (id,name,description,listId,selfLink, parentLink){
    this.id = id
    this.name = name
    this.description = description
    this.templateId = listId
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