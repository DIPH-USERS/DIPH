export class LoginToDashboard{
    public country: string;
    public state: string;
    public username: string;
    public password: string;
    public cycle: string;

    constructor(country:string, state:string, username:string, password: string) {
        this.country = country;
        this.state = state;
        this.username = username;
        this.password = password;
      }
}