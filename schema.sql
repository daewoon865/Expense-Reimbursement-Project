create database reimbursements;
use reimbursements;
create table Users (
	userId int not null auto_increment,
	userType varchar(64) not null,
    username varchar(64) unique not null,
    userpass varchar(64) not null,
    
    primary key (userId)
);

create table Sessions (
	sessionId int not null auto_increment,
    access varchar(64) not null,
    userId int not null,
    expiry long not null,    
    token varchar(128) unique not null,
    
    primary key(sessionId),
    foreign key (userId) references Users (userId)
);

create table ReimbursementRequest (
	requestId int not null auto_increment,
	sessionId int not null,
    employeeId int not null,
    reimbursementAmount long not null,
    employeeComments varchar(256) not null,
    
    primary key (requestId),
    foreign key (employeeId) references Users(userId),
    foreign key (sessionId) references Sessions(sessionId)
);

create table ReimbursementUpdate(
	updateId int primary key not null auto_increment,
    sessionToken varchar(128) not null,
    managerId int not null,
	reimbursementId int not null,    
    reimbursementStatus varchar(64) not null,
    managerComments varchar(256) not null, 	
    
    foreign key (managerId) references Users(userId),
    foreign key (reimbursementId) references ReimbursementRequest(requestId)
);

insert into Users (userType, username, userpass) values ("manager", "daewoon865@revature.net", "testpass");

-- drop table ReimbursementUpdate;
-- drop table ReimbursementRequest;
-- drop table Sessions;
-- drop table Users;