--
-- Turn off autocommit and start a transaction so that we can use the temp tables
--

SET AUTOCOMMIT FALSE;

START TRANSACTION;

--
-- Insert client information into the temporary tables. To add clients to the HSQL database, edit things here.
-- 
select * from client_response_type_TEMP;

INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client', 'secret', 'Test Client', false, null, 3600, 600, true),
  ('ab866e69-755c-4ab1-a859-ef42d71cb0df','AMsb-sf0Wg5zgPB1DKs9vDRzo0AfcNGBkP-Oi5jBTe2Jrpkjr0GrATZ7hhu8lTZRP1AmjVoAjm8ZZy3yqE24pNk','Spring Rest API',false,null,3600,600,true),
  ('061b7558-463e-4adb-8a47-cf22f334f06b','XifoBArqYrf9mqXaYaJwRWTSmvEbhekyJ22hPTO7eQbbMNxKa6Jv4MTRThimShETdxt_yUFhVK9CFbeU4KOXKQ','React Web App',false,null,3600,600,false);

INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client', 'openid'),
	('client', 'profile'),
	('client', 'email'),
	('client', 'address'),
	('client', 'phone'),
	('client', 'offline_access'),

  ('ab866e69-755c-4ab1-a859-ef42d71cb0df', 'openid'),
	('ab866e69-755c-4ab1-a859-ef42d71cb0df', 'profile'),
	('ab866e69-755c-4ab1-a859-ef42d71cb0df', 'email'),
	('ab866e69-755c-4ab1-a859-ef42d71cb0df', 'address'),
	('ab866e69-755c-4ab1-a859-ef42d71cb0df', 'phone'),
	('ab866e69-755c-4ab1-a859-ef42d71cb0df', 'offline_access'),

  ('061b7558-463e-4adb-8a47-cf22f334f06b', 'openid'),
	('061b7558-463e-4adb-8a47-cf22f334f06b', 'profile'),
	('061b7558-463e-4adb-8a47-cf22f334f06b', 'email'),
	('061b7558-463e-4adb-8a47-cf22f334f06b', 'address'),
	('061b7558-463e-4adb-8a47-cf22f334f06b', 'phone'),
	('061b7558-463e-4adb-8a47-cf22f334f06b', 'offline_access');

INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client', 'http://localhost/'),
	('client', 'http://localhost:8080/');
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client', 'authorization_code'),
	('client', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client', 'urn:ietf:params:oauth:grant-type:device_code'),
	('client', 'implicit'),
	('client', 'refresh_token'),
  
  ('ab866e69-755c-4ab1-a859-ef42d71cb0df', 'implicit'),
	('ab866e69-755c-4ab1-a859-ef42d71cb0df', 'authorization_code'),
  
  ('061b7558-463e-4adb-8a47-cf22f334f06b', 'implicit'),
	('061b7558-463e-4adb-8a47-cf22f334f06b', 'authorization_code');
	
     
INSERT INTO client_response_type_TEMP(owner_id, response_type) VALUES
  ('ab866e69-755c-4ab1-a859-ef42d71cb0df','code'),
  ('061b7558-463e-4adb-8a47-cf22f334f06b','token id_token');

--
-- Merge the temporary clients safely into the database. This is a two-step process to keep clients from being created on every startup with a persistent store.
--

MERGE INTO client_details 
  USING (SELECT client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection FROM client_details_TEMP) AS vals(client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection)
  ON vals.client_id = client_details.client_id
  WHEN NOT MATCHED THEN 
    INSERT (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES(client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection);

MERGE INTO client_scope 
  USING (SELECT id, scope FROM client_scope_TEMP, client_details WHERE client_details.client_id = client_scope_TEMP.owner_id) AS vals(id, scope)
  ON vals.id = client_scope.owner_id AND vals.scope = client_scope.scope
  WHEN NOT MATCHED THEN 
    INSERT (owner_id, scope) values (vals.id, vals.scope);

MERGE INTO client_redirect_uri 
  USING (SELECT id, redirect_uri FROM client_redirect_uri_TEMP, client_details WHERE client_details.client_id = client_redirect_uri_TEMP.owner_id) AS vals(id, redirect_uri)
  ON vals.id = client_redirect_uri.owner_id AND vals.redirect_uri = client_redirect_uri.redirect_uri
  WHEN NOT MATCHED THEN 
    INSERT (owner_id, redirect_uri) values (vals.id, vals.redirect_uri);

MERGE INTO client_grant_type 
  USING (SELECT id, grant_type FROM client_grant_type_TEMP, client_details WHERE client_details.client_id = client_grant_type_TEMP.owner_id) AS vals(id, grant_type)
  ON vals.id = client_grant_type.owner_id AND vals.grant_type = client_grant_type.grant_type
  WHEN NOT MATCHED THEN 
    INSERT (owner_id, grant_type) values (vals.id, vals.grant_type);
    
 
 MERGE INTO client_response_type 
  USING (SELECT id, response_type FROM client_response_type_TEMP, client_details WHERE client_details.client_id = client_response_type_TEMP.owner_id) AS vals(id, response_type)
  ON vals.id = client_response_type.owner_id AND vals.response_type = client_response_type.response_type
  WHEN NOT MATCHED THEN 
    INSERT (owner_id, response_type) values (vals.id, vals.response_type);
    

-- 
-- Close the transaction and turn autocommit back on
-- 
    
COMMIT;

SET AUTOCOMMIT TRUE;

