# simplekanban
simple kanban (trello like) board api

### endpoints:
#### board:
* **/api/v1/boards**  
**GET** - list all boards  
**POST** - create new board  

* **/api/v1/boards/{id}**  
**GET** - get board info    
**PUT** - update board   
**DELETE** - delete board  

object for create and update board:
```
{
    "name": "string"
}
```  

#### lists:
* **/api/v1/boards/{id}/lists**  
**GET** - get list(columns) of board  
**POST** - create list(column) for board  
* **/api/vi/lists/{id}  
**GET** - get list info  
**PUT** - update list  
**DELETE** - delete list

object for create and update list:  
```
{
    "name": "string",
    "position": float
}
```

_"position" required only if position changed_  
_value calculated by rule:_  
* _for first position: previous first object position divided by 2_
* _for last position: previous last object position plus 65536 (0x10000)_
* _for position between other objects: set middle of objects positions._  

#### cards:
* **/api/v1/lists/{id}/cards**  
**GET** - get cards of list  
**POST** - add card for list
* **/api/v1/cards/{id}**  
**GET** - get card info  
**PUT** - update card  
**DELETE** - delete card  

object for create card:  
```
{
    "title": "string",
    "description": "string"
}
```
object for update card:  
```
{
    "title": "string",
    "description": "string",
    "position": float,
    "listId": long
}
```
_position rule some as for list_  
_"description" is optional_  
_"listId" required only if card move to another list_

 
