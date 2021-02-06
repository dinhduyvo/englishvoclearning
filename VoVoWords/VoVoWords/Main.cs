using System;
using System.Threading;
using System.Windows.Forms;
using VoVoWords.EF;

namespace VoVoWords
{
    public partial class Main : Form
    {
        private bool IsShowBalloon = false;
        private int TimeOut = 4000;
        ApplicationDbContext DbContext;
        public Main()
        {
            // DbContext = new ApplicationDbContext();
            InitializeComponent();
        }

        private void Main_Resize(object sender, EventArgs e)
        {
            if (this.WindowState == FormWindowState.Minimized)
            {
                Hide();
                notifyIcon.Visible = true;
                IsShowBalloon = true;
                StartShowBalloon();
            }
        }

        private void notifyIcon_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            Show();
            this.WindowState = FormWindowState.Normal;
            notifyIcon.Visible = false;
        }

        private void StartShowBalloon()
        {
            Random objRandom = new Random(10);

            while (true)
            {
                notifyIcon.BalloonTipTitle = "Hello " + objRandom.Next();
                notifyIcon.BalloonTipText = "Hello " + objRandom.Next();
                notifyIcon.ShowBalloonTip(TimeOut);
                Thread.Sleep(TimeOut);
            }
            
        }

        private void notifyIcon_Click(object sender, EventArgs e)
        {
            // contextMenuStrip.Show();
        }
    }
}
