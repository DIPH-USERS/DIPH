// form1a.ts

export default class form1aModel {
    date_of_verification: string;
    state_policy: [];

    constructor(date_of_verification:string,state_policy: []) {
      this.date_of_verification = date_of_verification;
      this.state_policy = state_policy;
    }
  }