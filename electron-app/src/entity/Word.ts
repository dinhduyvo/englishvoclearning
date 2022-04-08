import {Entity, PrimaryGeneratedColumn, Column} from "typeorm";

@Entity()
export class Word {

    @PrimaryGeneratedColumn()
    id: number | undefined;

    @Column()
    word: string | undefined;

    @Column()
    meaning: string | undefined;

    @Column()
    description: string | undefined;

    @Column()
    remembered: boolean | undefined;

}
