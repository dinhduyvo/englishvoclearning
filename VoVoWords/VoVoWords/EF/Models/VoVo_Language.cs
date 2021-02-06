using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VoVoWords.EF.Models
{
    public class VoVo_Language : BaseEntity
    {
        public Guid Id { get; set; }
        public string Name { get; set; }
        public bool Active { get; set; }
        public bool Active2 { get; set; }
    }
}
