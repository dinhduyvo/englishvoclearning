namespace VoVoWords.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Initial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.VoVo_Language",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Name = c.String(),
                        Active = c.Boolean(nullable: false),
                        CreatedDate = c.DateTime(nullable: false),
                        UpdatedDate = c.DateTime(nullable: false),
                        CreatedBy = c.Guid(nullable: false),
                        UpdatedBy = c.Guid(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.VoVo_Word",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Word = c.String(),
                        Meaning = c.String(),
                        CreatedDate = c.DateTime(nullable: false),
                        UpdatedDate = c.DateTime(nullable: false),
                        CreatedBy = c.Guid(nullable: false),
                        UpdatedBy = c.Guid(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
        }
        
        public override void Down()
        {
            DropTable("dbo.VoVo_Word");
            DropTable("dbo.VoVo_Language");
        }
    }
}
