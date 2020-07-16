import { User } from 'src/app/shared/models/user/user';


export class PublicationComment {

    private _id: number;
    private _user: User;
    private _text: String;
    private _votes: number;
    private _comments: String [];
    private _dateOfComment: Date;

 


    constructor(data: any) {
        this.id = data.id;
        this.text = data.text;
        this.user = data.user;
        this._votes = data.votes;
        this._comments = [];
        this.dateOfComment = data.dateOfComment;
      
    }

    public get user(): User {
        return this._user;
    }

    public set user(user: User) {
        this._user = user;
    }

    public get id(): number {
        return this._id;
    }

    public set id(value: number) {
        this._id = value;
    }

    public get votes(): number {
        return this._votes;
    }

    public set votes(value: number) {
        this._votes = value;
    }
    public get text(): String {
        return this._text;
    }
    
    public set text(value: String) {
        this._text = value;
    }
 
    public get comments(): String[] {
        return this._comments;
    }
    
    public set comments(value: String []) {
        this._comments = value;
    }

    public get dateOfComment(): Date {
        return this._dateOfComment;
    }
    
    public set dateOfComment(value: Date) {
        this._dateOfComment = value;
    }
}