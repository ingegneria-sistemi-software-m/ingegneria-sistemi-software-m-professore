%====================================================================================
% storageramservice description   
%====================================================================================
dispatch( createItem, item(ITEMID,JSONSTR) ).
request( getItem, item(ITEMID) ).
reply( getItemAnswer, item(JSONSTR) ).  %%for getItem
dispatch( deleteItem, item(ITEMID) ).
request( getAllItems, dummy(ID) ).
reply( getAllItemsAnswer, items(STRING) ).  %%for getAllItems
%====================================================================================
context(ctxstorageram, "localhost",  "TCP", "8110").
 qactor( storagevolatile, ctxstorageram, "it.unibo.storagevolatile.Storagevolatile").
 static(storagevolatile).
