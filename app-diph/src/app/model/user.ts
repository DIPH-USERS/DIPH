export class User{
    username: string;   
    status:string;
    userStatus:string    
    id:string;

    constructor(username:string, status:string, userStatus:string,id:string) {
        this.username = username;        
        this.status = status;      
        this.id = id;
      }
    
}