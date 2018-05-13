


export default function (requestBody){

  if(!requestBody._embedded)
    return new ListModel([],'')//empty list


  return new ListModel(
    requestBody._embedded.checklistResourceList,
    requestBody._links.self.href
  )
}


class ListModel{

  constructor (items,selfLink){
    this.items = items.map(item => {
      return {
        id: item.checklist.id,
        name: item.checklist.name,
        selfLink: item._links.self.href
      }
    })

    this.selfLink = selfLink
  }
}

/*
{
    "_embedded": {
        "checklistResourceList": [
            {
                "checklist": {
                    "id": 1,
                    "name": "mychecklist"
                },
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/listing/checklist/1"
                    },
                    "items": {
                        "href": "http://localhost:8080/listing/checklist/1/items"
                    },
                    "user": {
                        "href": "http://localhost:8080/listing/user/bnVubzoxMjM0NQ=="
                    }
                }
            }
        ]
    },
    "_links": {
        "self": [
            {
                "href": "http://localhost:8080/listing/checklists"
            },
            {
                "href": "http://localhost:8080/listing/user/bnVubzoxMjM0NQ=="
            }
        ]
    }
}
 */