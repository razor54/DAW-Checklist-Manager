# *DAW PROJECT*

# Structure of The Project
- - - -
## Control - registered controllers
* AuthenticationController - responsible for the authentication request
* ServiceController - responsible for the funcional requests
- - - -
## Domain
* AuthCredentials - http basic authentication helper 
### Model - JSON objects representations
* Checklist 
* ChecklistItem
* Template
* TemplateItem
* User
### Resource - wrappers to add functionalities to model objects
* ChecklistResource
* ChecklistItemResource
* TemplateResource
* TemplateItemResource
* UserResource
- - - -
## Exceptions
* FailedAddChecklistException
* FailedAddChecklistItemException
* FailedAddUserException
* ForbiddenException
* MyException(Abstract) - Base exception class
* NoSuchChecklistException
* NoSuchChecklistItemException
* NoSuchTemplateException
* NoSuchUserException
* NoSuchTemplateItemException
* UnauthorizedException
- - - -
## Handlers - registered handlers 
* MyExceptionHandler - responsible for handle exceptions and return problem+json objects
- - - -
## Repository - communication with database
* ChecklistRepository
* ChecklistItemRepository
* TemplateRepository
* TemplateItemRepository
* UserRepository
- - - -
## Resolvers - registered resolvers
* AuthArgumentResolver - responsible for retrieve authentication header
- - - -
## Service - retrieves data from repository and returns a model object
* ChecklistService
* ChecklistItemService
* TemplateService
* TemplateItemService
* UserService










