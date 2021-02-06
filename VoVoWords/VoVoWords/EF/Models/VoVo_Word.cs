using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VoVoWords.EF.Models
{
    public class VoVo_Word : BaseEntity
    {
        public Guid Id { get; set; }
        public string Word { get; set; }
        public string Meaning { get; set; }

    }
}
