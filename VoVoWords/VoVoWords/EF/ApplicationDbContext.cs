using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.Common;
using System.Data.Entity;
using System.Data.Entity.Migrations;
using System.Data.Entity.Migrations.History;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using VoVoWords.EF.Models;

namespace VoVoWords.EF
{
    public class ApplicationDbContext : DbContext
    {
        public DbSet<VoVo_Word> VoVo_Words { get; set; }
        public DbSet<VoVo_Language> VoVo_Languages { get; set; }
        public ApplicationDbContext() : base(GetConnection(), false)
        {
            Database.SetInitializer(new MigrateDatabaseToLatestVersion<ApplicationDbContext, VoVoWords.Migrations.Configuration>(true));
        }

        public static DbConnection GetConnection()
        {
            var connection = ConfigurationManager.ConnectionStrings["SQServerConnection"];
            var factory = DbProviderFactories.GetFactory(connection.ProviderName);
            var dbCon = factory.CreateConnection();
            dbCon.ConnectionString = connection.ConnectionString;
            return dbCon;
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Entity<VoVo_Language>().ToTable("VoVo_Language");
            modelBuilder.Entity<VoVo_Word>().ToTable("VoVo_Word");
        }
    }
}
