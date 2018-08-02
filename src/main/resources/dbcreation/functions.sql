CREATE OR REPLACE FUNCTION ifUserExists(login TEXT, email TEXT) RETURNS BOOLEAN AS $$
BEGIN
IF ((SELECT userlogin FROM users WHERE userlogin ILIKE login) IS NOT NULL OR (SELECT useremail FROM users WHERE useremail = email) IS NOT NULL) THEN
RETURN true;
ELSE
RETURN false;
END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION ifArtifactExists(name_ TEXT) RETURNS BOOLEAN AS $$
BEGIN
IF (SELECT artifactname FROM artifact WHERE artifactname ILIKE name_) IS NOT NULL THEN
RETURN true;
ELSE
RETURN false;
END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION ifQuestExists(name_ TEXT) RETURNS BOOLEAN AS $$
BEGIN
IF (SELECT questname FROM quest WHERE questname ILIKE name_) IS NOT NULL THEN
RETURN true;
ELSE
RETURN false;
END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION ifClassExists(description TEXT) RETURNS BOOLEAN AS $$
BEGIN
IF (SELECT classdescription FROM class WHERE classdescription ILIKE description) IS NOT NULL THEN
RETURN true;
ELSE
RETURN false;
END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION ifLevelExists(title TEXT) RETURNS BOOLEAN AS $$
BEGIN
IF (SELECT leveltitle FROM levelchart WHERE leveltitle ILIKE title) IS NOT NULL THEN
RETURN true;
ELSE
RETURN false;
END IF;
END;
$$ LANGUAGE plpgsql;

    CREATE OR REPLACE FUNCTION createMentor (surname TEXT, name TEXT, email TEXT, login TEXT, password TEXT, address TEXT) RETURNS void AS $$
    DECLARE
    idrole INTEGER;
    iduser_ INTEGER;
    BEGIN
 SELECT idcodecoolrole FROM codecoolrole WHERE roledescription = 'mentor' INTO idrole;
 INSERT INTO users (usersurname, username, useremail, userlogin, userpassword, useraddress, idcodecoolrole)
 VALUES (surname, name, email, login, password, address, idrole);
 SELECT iduser FROM users WHERE useremail = email INTO iduser_;
 INSERT INTO mentor (iduser)
 VALUES (iduser_);
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

 CREATE OR REPLACE FUNCTION createStudent(userSurname text, username text, useremail text, userlogin text, userpass text, githubacc text, classid integer)
 RETURNS void AS $$
 BEGIN
 INSERT INTO student(iduser, idclass) VALUES (createUser(userSurname, username, useremail, userlogin, userpass, githubacc, 3), classid);
 END;
 $$ LANGUAGE plpgsql;

 CREATE OR REPLACE FUNCTION createUser(surname text, firstname text, mail text, login text, userpass text, address TEXT, idrole integer)
 RETURNS integer AS $userid$
 BEGIN
 INSERT INTO users(usersurname, username, useremail, userlogin, userpassword, useraddress, idcodecoolrole)
 VALUES (surname, firstname, mail, login, userpass, address, idrole);
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

create or replace function markQuest (idstudent_ integer, idquest_ integer)
returns void as
$$
begin
    update questhistory set status='DONE' 
    where idquest = idquest_ and idstudent = idstudent_ and status!='DONE';
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

CREATE OR REPLACE FUNCTION loginValidation(login TEXT, password TEXT) RETURNS INTEGER AS $$
BEGIN
RETURN (SELECT iduser FROM users WHERE userlogin = login AND userpassword = password);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getMentor(userid INTEGER) RETURNS TABLE (name_ TEXT, surname TEXT, email TEXT, adress TEXT) AS $$
BEGIN
RETURN QUERY (SELECT username, usersurname, useremail, mentoraddress FROM mentor JOIN users ON users.iduser = mentor.iduser WHERE users.iduser = userid);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getStudent(userid INTEGER) RETURNS TABLE (name_ TEXT, surname TEXT, email TEXT, github TEXT) AS $$
BEGIN
RETURN QUERY (SELECT username, usersurname, useremail, githubadress FROM student JOIN users ON users.iduser = student.iduser WHERE users.iduser = userid);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION takeQuest(userid INTEGER, questid INTEGER) RETURNS VOID AS $$
BEGIN
INSERT INTO questhistory(idstudent, idquest, value, status)
VALUES ((SELECT idstudent FROM student WHERE iduser = userid), questid, (SELECT questvalue FROM quest WHERE idquest = questid), 'IN PROGRESS');
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getExp(userid INTEGER) RETURNS INTEGER AS $$
BEGIN
RETURN (SELECT SUM(value) FROM questhistory WHERE idstudent = (SELECT idstudent FROM student WHERE iduser = userid) AND status LIKE 'DONE');
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION deleteUser(userid INTEGER) RETURNS VOID AS $$
BEGIN
DELETE FROM users WHERE iduser = userid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION updateEmail(userid INTEGER, email TEXT) RETURNS VOID AS $$
BEGIN
UPDATE users
SET useremail = email
WHERE iduser = userid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION updateAddress(userid INTEGER, address TEXT) RETURNS VOID AS $$
BEGIN
UPDATE users
SET useraddress = address
WHERE iduser = userid;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION updateArtifact(artifactid INTEGER, name_ TEXT, description TEXT, value INTEGER, first_image TEXT, second_image TEXT) RETURNS VOID AS $$
BEGIN
UPDATE artifact
SET artifactname = name_, artifactdescription = description, currentartifactcost = value, image = first_image, image_marked = second_image
WHERE idartifact = artifactid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION updateQuest(questid INTEGER, name_ TEXT, description TEXT, value INTEGER, first_image TEXT, second_image TEXT) RETURNS VOID AS $$
BEGIN
UPDATE quest
SET questname = name_, questdescription = description, questvalue = value, image = first_image, image_marked = second_image
WHERE idquest = questid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getQuest(questid INTEGER) RETURNS TABLE (name TEXT, description TEXT, value INTEGER, first_image TEXT, second_marked TEXT, questcategory INTEGER) AS $$
BEGIN
RETURN QUERY (SELECT questname, questdescription, questvalue, image, image_marked, idquestcategory FROM quest WHERE idquest = questid);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getArtifact(artifactid INTEGER) RETURNS TABLE (name TEXT, description TEXT, value INTEGER, first_image TEXT, second_marked TEXT, questcategory INTEGER) AS $$
BEGIN
RETURN QUERY (SELECT artifactname, artifactdescription, currentartifactcost, image, image_marked, idartifactcategory FROM artifact WHERE idartifact = artifactid);
END;
$$ LANGUAGE plpgsql;	

CREATE OR REPLACE FUNCTION getStudentQuests(userid INTEGER) RETURNS TABLE (historyid INTEGER, questid INTEGER, value_ INTEGER, date_ DATE, status_ TEXT) AS $$
DECLARE 
studentid INTEGER;
BEGIN
SELECT idstudent FROM student WHERE iduser = userid INTO studentid;
RETURN QUERY (SELECT idquesthistory, idquest, value, date, status FROM questhistory WHERE idstudent = studentid);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getStudentPersonalArtifacts(userid INTEGER) RETURNS TABLE (historyid INTEGER, artifactid INTEGER, cost_ INTEGER, date_ DATE, used BOOLEAN) AS $$
DECLARE
studentid INTEGER;
BEGIN
SELECT idstudent FROM student WHERE iduser = userid INTO studentid;
RETURN QUERY (SELECT idpersonalartifacthistory, idartifact, cost, date, isused FROM personalartifacthistory WHERE idstudent = studentid);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getUser(id INTEGER) RETURNS TABLE (name TEXT, surname TEXT, email TEXT, address TEXT, role TEXT) AS $$
DECLARE
rolee INTEGER;
desc TEXT;
BEGIN
SELECT idcodecoolrole FROM users WHERE iduser = id INTO rolee;
RETURN QUERY (SELECT username, usersurname, useremail, useraddress, (SELECT roledescription FROM codecoolrole WHERE idcodecoolrole = rolee) FROM users WHERE iduser = id);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getMentors() RETURNS TABLE (name TEXT, surname TEXT, email TEXT, address TEXT, id INTEGER) AS $$
BEGIN
RETURN QUERY (SELECT username, usersurname, useremail, useraddress, iduser FROM users WHERE idcodecoolrole = 2);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getClassStudents(classid INTEGER) RETURNS TABLE (userid INTEGER, name TEXT, surname TEXT, email TEXT, address TEXT) AS $$
BEGIN
RETURN QUERY (SELECT users.iduser, username, usersurname, useremail, useraddress FROM users JOIN student ON users.iduser = student.iduser WHERE idclass = classid);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getClassMentors(classid INTEGER) RETURNS TABLE (userid INTEGER, name TEXT, surname TEXT, email TEXT, address TEXT) AS $$
BEGIN
RETURN QUERY (SELECT users.iduser, username, usersurname, useremail, useraddress FROM users JOIN mentor ON users.iduser = mentor.iduser JOIN mentor_class ON mentor.idmentor = mentor_class.idmentor WHERE idclass = classid);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getClasses() RETURNS TABLE (id INTEGER, description TEXT) AS $$
BEGIN
RETURN QUERY (SELECT idclass, classdescription FROM class);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getStudents() RETURNS TABLE (name TEXT, surname TEXT, email TEXT, address TEXT, id INTEGER) AS $$
BEGIN
RETURN QUERY (SELECT username, usersurname, useremail, useraddress, iduser FROM users WHERE idcodecoolrole = 3);
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION getUsers() RETURNS TABLE (id INTEGER, name TEXT, surname TEXT, email TEXT, address TEXT, role TEXT) AS $$
DECLARE
desc TEXT;
BEGIN
RETURN QUERY (select iduser, username, usersurname, useremail, useraddress, roledescription from users join codecoolrole on (users.idcodecoolrole = codecoolrole.idcodecoolrole));
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getUsers(role TEXT) RETURNS TABLE (id INTEGER, name TEXT, surname TEXT, email TEXT, address TEXT) AS $$
DECLARE
desc TEXT;
BEGIN
RETURN QUERY (select iduser, username, usersurname, useremail, useraddress from users join codecoolrole on (users.idcodecoolrole = codecoolrole.idcodecoolrole) WHERE roledescription=role);
END;
$$ LANGUAGE plpgsql;
