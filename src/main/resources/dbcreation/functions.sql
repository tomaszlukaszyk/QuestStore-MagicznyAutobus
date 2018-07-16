
    CREATE OR REPLACE FUNCTION createMentor (surname TEXT, name TEXT, email TEXT, login TEXT, password TEXT, address TEXT) RETURNS void AS $$
    DECLARE
    idrole INTEGER;
    iduser_ INTEGER;
    BEGIN
 SELECT idcodecoolrole FROM codecoolrole WHERE roledescription = 'mentor' INTO idrole;
 INSERT INTO users (usersurname, username, useremail, userlogin, userpassword, idcodecoolrole)
 VALUES (surname, name, email, login, password, idrole);
 SELECT iduser FROM users WHERE useremail = email INTO iduser_;
 INSERT INTO mentor (mentoraddress, iduser)
 VALUES (address, iduser_);
 END;
 $$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION createArtifact (name TEXT, description TEXT, cost INTEGER, isgroup BOOLEAN, first_image TEXT, second_image TEXT) RETURNS void AS $$
 DECLARE idcategory INTEGER;
 BEGIN
 SELECT idartifactcategory FROM artifactcategory WHERE isgroupartifact = isgroup LIMIT 1 INTO idcategory;
 INSERT INTO artifact (artifactname, artifactdescription, currentartifactcost, idartifactcategory, image, image_marked)
 VALUES (name, description, cost, idcategory, first_image, second_image);
 END;
 $$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION createQuest (name TEXT, description TEXT, value INTEGER, isgroup BOOLEAN, first_image TEXT, second_image TEXT) RETURNS void AS $$
 DECLARE
 idcategory INTEGER;
 BEGIN
 SELECT idquestcategory FROM questcategory WHERE isgroupquest = isgroup LIMIT 1 INTO idcategory;
 INSERT INTO quest (questname, questdescription, questvalue, idquestcategory, image, image_marked)
 VALUES (name, description, value, idcategory, first_image, second_image);
 END;
 $$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION editMentor (mentorid INTEGER, email TEXT, tochangeclassid INTEGER, newclassid INTEGER) RETURNS void AS $$
 DECLARE userid INTEGER;
 BEGIN
 UPDATE mentor_class
 SET idclass = newclassid
 WHERE idmentor = mentorid AND idclass = tochangeclassid;
 SELECT iduser FROM mentor WHERE idmentor = mentorid INTO userid;
 UPDATE users
 SET useremail = email
 WHERE iduser = userid;
 END;
 $$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION sumQuestsGainForStudent (id INTEGER) RETURNS INTEGER AS $$
 BEGIN
 RETURN (SELECT COALESCE(SUM(quest.questvalue), 0) FROM questhistory JOIN quest ON questhistory.idquest = quest.idquest WHERE idstudent = id);
 END;
 $$ LANGUAGE plpgsql;
 CREATE OR REPLACE FUNCTION sumArtifactsCostForStudent (id INTEGER) RETURNS INTEGER AS $$
 BEGIN
 RETURN (SELECT COALESCE(SUM(personalartifacthistory.cost), 0) FROM personalartifacthistory WHERE idstudent = id);
 END;
 $$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION showWallet (id INTEGER) RETURNS INTEGER AS $$
 BEGIN
 RETURN (SELECT sumQuestsGainForStudent(id) - sumArtifactsCostForStudent(id) FROM student WHERE idstudent = id);
 END;
 $$ LANGUAGE plpgsql;
 CREATE OR REPLACE FUNCTION showStudentsWallets () RETURNS TABLE (StudentId INTEGER, Wallet INTEGER) AS $$
 BEGIN
 RETURN QUERY (SELECT idstudent, showWallet(idstudent) FROM student);
 END;
 $$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION createClass(description text)
 RETURNS void AS $$
 BEGIN
 INSERT INTO class(classdescription) VALUES (description);
 END;
 $$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION assignMentor(idMentor integer, idClass integer)
 RETURNS void AS $$
 BEGIN
 INSERT INTO mentor_class VALUES (idMentor, idClass);
 END;
 $$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION createStudent(userSurname text, username text, useremail text, userlogin text, userpass text, githubacc text)
 RETURNS void AS $$
 BEGIN
 INSERT INTO student(githubadress, iduser) VALUES (githubacc, createUser(userSurname, username, useremail, userlogin, userpass, 3));
 END;
 $$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION createUser(surname text, firstname text, mail text, login text, userpass text, idrole integer)
 RETURNS integer AS $userid$
 BEGIN
 INSERT INTO users(usersurname, username, useremail, userlogin, userpassword, idcodecoolrole)
 VALUES (surname, firstname, mail, login, userpass, idrole);
 RETURN (SELECT iduser FROM users WHERE users.usersurname = surname AND users.username = firstname AND users.useremail = mail AND users.userlogin = login AND users.userpassword = userpass AND users.idcodecoolrole = idrole);
 END;
 $userid$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION markArtifact(personalartifactid integer)
 RETURNS void AS $$
 BEGIN
 UPDATE personalartifacthistory
 SET isused = true
WHERE personalartifacthistory.idpersonalartifacthistory = personalartifactid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION updateartifact(artid integer, newcost integer)
RETURNS void AS $updatedartifact$
BEGIN
UPDATE artifact SET currentartifactcost = newcost
WHERE artid = idartifact;
END;
$updatedartifact$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION updateQuest(questid integer, newcost integer)
RETURNS void AS $updatedartifact$
BEGIN
UPDATE quest SET questvalue = newcost
WHERE questid = idquest;
END;
$updatedartifact$ LANGUAGE plpgsql;

create or replace function showmentorprofile (email_ text)
returns table (
    name_ text,
    surname text,
    email text,
    adress text
) AS 
$$
begin
    return query
    select username, usersurname, useremail, mentoraddress 
    from mentor join users on users.iduser = mentor.iduser
    where useremail=email_;
end;
$$ language plpgsql;

create or replace function markQuest (idstudent_ integer, idquest_ integer)
returns void as
$$
begin
    update questhistory set status='accepted' 
    where idquest=idquest_ and idstudent=idstudent_ and status!='accepted';
end;
$$ language plpgsql;

create or replace function buypersonalartifact(idartifact_ int, idstudent_ int)
returns void as
$$
declare 
artifactcost int = (
    select currentartifactcost
    from artifact
    where idartifact=idartifact_
);
begin
    insert into personalartifacthistory(idartifact, idstudent, cost) values (idartifact_, idstudent_, artifactcost);
end;
$$ language plpgsql;

create or replace function addexplevel(leveltitle_ text)
returns void as
$$
begin
    insert into levelchart(leveltitle) values (leveltitle_);
end;
$$ language plpgsql;

create or replace function verifyexplevel(exppoints int)
returns int as
$$
declare 
    explevel int = 1;
    power2 int = 1;
    x int = 1;
    n int = 2; 
begin
    if exppoints = 1 then return 1; end if;
    while x < exppoints loop
        explevel = explevel + 1;
        x = power(n, power2);
        power2 = power2 + 1;        
    end loop;
    return explevel - 1;
end;
$$ language plpgsql;
