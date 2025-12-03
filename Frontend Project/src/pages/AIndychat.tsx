import { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { RoleBasedNavbar } from '@/components/navbar';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Card } from '@/components/ui/card';
import { Loader2, Send, Sparkles, ArrowLeft } from 'lucide-react';
import { supabase } from '@/integrations/supabase/client';
import { useToast } from '@/hooks/use-toast';
import { motion, AnimatePresence } from 'framer-motion';

interface Message {
  role: 'user' | 'assistant';
  content: string;
  timestamp: Date;
}

const AindyChat = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [messages, setMessages] = useState<Message[]>([
    {
      role: 'assistant',
      content: '¡Hola! 👋 Soy AINDY, tu asistente virtual del gimnasio Icesi. Estoy aquí para ayudarte con tus entrenamientos, responder dudas sobre ejercicios, nutrición deportiva y motivarte en tu camino fitness. ¿En qué puedo ayudarte hoy?',
      timestamp: new Date(),
    },
  ]);
  const [input, setInput] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const scrollRef = useRef<HTMLDivElement>(null);
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (scrollRef.current) {
      scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
    }
  }, [messages]);

  const handleSend = async () => {
    if (!input.trim() || isLoading) return;

    const userMessage: Message = {
      role: 'user',
      content: input.trim(),
      timestamp: new Date(),
    };

    setMessages((prev) => [...prev, userMessage]);
    setInput('');
    setIsLoading(true);

    try {
      const messagesToSend = [...messages, userMessage].map((msg) => ({
        role: msg.role,
        content: msg.content,
      }));

      console.log('Sending messages to function:', messagesToSend);

      const { data, error } = await supabase.functions.invoke('aindy-chat', {
        body: {
          messages: messagesToSend,
        },
      });

      if (error) {
        console.error('Supabase function error:', error);
        throw error;
      }

      if (!data || !data.message) {
        console.error('Invalid response from function:', data);
        throw new Error('Respuesta inválida del servidor');
      }

      const assistantMessage: Message = {
        role: 'assistant',
        content: data.message,
        timestamp: new Date(),
      };

      setMessages((prev) => [...prev, assistantMessage]);
    } catch (error: any) {
      console.error('Error sending message:', error);
      
      let errorMessage = 'No se pudo enviar el mensaje. Por favor intenta de nuevo.';
      
      if (error?.message) {
        errorMessage = error.message;
      } else if (error?.error) {
        errorMessage = error.error;
      } else if (typeof error === 'string') {
        errorMessage = error;
      }

      toast({
        title: 'Error',
        description: errorMessage,
        variant: 'destructive',
      });

      // Remover el mensaje del usuario si falló
      setMessages((prev) => prev.slice(0, -1));
    } finally {
      setIsLoading(false);
      inputRef.current?.focus();
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSend();
    }
  };

  return (
    <div className="min-h-screen bg-background">
      <RoleBasedNavbar />
      
      <main className="container mx-auto px-4 py-8 max-w-5xl">
        <div className="mb-6 flex items-center gap-4">
          <Button
            variant="ghost"
            size="icon"
            onClick={() => navigate('/app')}
            className="hover:bg-accent"
          >
            <ArrowLeft className="h-5 w-5" />
          </Button>
          <div className="flex items-center gap-3">
            <div className="relative">
              <Sparkles className="h-8 w-8 text-primary" />
              <motion.div
                className="absolute -inset-1 bg-primary/20 rounded-full blur-md"
                animate={{
                  scale: [1, 1.2, 1],
                  opacity: [0.5, 0.8, 0.5],
                }}
                transition={{
                  duration: 2,
                  repeat: Infinity,
                  ease: "easeInOut",
                }}
              />
            </div>
            <div>
              <h1 className="text-3xl font-bold tracking-tight">AINDY</h1>
              <p className="text-sm text-muted-foreground">Tu asistente virtual de fitness</p>
            </div>
          </div>
        </div>

        <Card className="h-[calc(100vh-280px)] flex flex-col border-2 shadow-lg">
          <ScrollArea className="flex-1 p-6" ref={scrollRef}>
            <AnimatePresence mode="popLayout">
              {messages.map((message, index) => (
                <motion.div
                  key={index}
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.3 }}
                  className={`mb-6 flex ${
                    message.role === 'user' ? 'justify-end' : 'justify-start'
                  }`}
                >
                  <div
                    className={`max-w-[80%] rounded-2xl px-6 py-4 ${
                      message.role === 'user'
                        ? 'bg-primary text-primary-foreground ml-auto'
                        : 'bg-secondary text-secondary-foreground'
                    }`}
                  >
                    {message.role === 'assistant' && (
                      <div className="flex items-center gap-2 mb-2">
                        <Sparkles className="h-4 w-4" />
                        <span className="text-xs font-semibold uppercase tracking-wide">AINDY</span>
                      </div>
                    )}
                    <p className="whitespace-pre-wrap leading-relaxed">{message.content}</p>
                    <p className="text-xs opacity-60 mt-2">
                      {message.timestamp.toLocaleTimeString('es-ES', {
                        hour: '2-digit',
                        minute: '2-digit',
                      })}
                    </p>
                  </div>
                </motion.div>
              ))}
            </AnimatePresence>
            
            {isLoading && (
              <motion.div
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                className="flex justify-start mb-6"
              >
                <div className="bg-secondary text-secondary-foreground rounded-2xl px-6 py-4 max-w-[80%]">
                  <div className="flex items-center gap-2 mb-2">
                    <Sparkles className="h-4 w-4" />
                    <span className="text-xs font-semibold uppercase tracking-wide">AINDY</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Loader2 className="h-4 w-4 animate-spin" />
                    <span className="text-sm">Pensando...</span>
                  </div>
                </div>
              </motion.div>
            )}
          </ScrollArea>

          <div className="border-t p-4">
            <div className="flex gap-2">
              <Input
                ref={inputRef}
                value={input}
                onChange={(e) => setInput(e.target.value)}
                onKeyPress={handleKeyPress}
                placeholder="Escribe tu mensaje..."
                disabled={isLoading}
                className="flex-1 border-2 focus-visible:ring-2 focus-visible:ring-primary"
              />
              <Button
                onClick={handleSend}
                disabled={isLoading || !input.trim()}
                size="icon"
                className="shrink-0"
              >
                {isLoading ? (
                  <Loader2 className="h-4 w-4 animate-spin" />
                ) : (
                  <Send className="h-4 w-4" />
                )}
              </Button>
            </div>
            <p className="text-xs text-muted-foreground mt-2 text-center">
              AINDY puede cometer errores. Considera verificar información importante.
            </p>
          </div>
        </Card>

        <div className="mt-6 grid grid-cols-1 md:grid-cols-3 gap-4">
          <Card className="p-4 hover:border-primary/50 transition-colors cursor-pointer" onClick={() => setInput('¿Qué ejercicios recomiendas para principiantes?')}>
            <h3 className="font-semibold mb-2">💪 Ejercicios</h3>
            <p className="text-sm text-muted-foreground">Rutinas y técnicas</p>
          </Card>
          <Card className="p-4 hover:border-primary/50 transition-colors cursor-pointer" onClick={() => setInput('¿Qué debo comer antes de entrenar?')}>
            <h3 className="font-semibold mb-2">🥗 Nutrición</h3>
            <p className="text-sm text-muted-foreground">Consejos alimenticios</p>
          </Card>
          <Card className="p-4 hover:border-primary/50 transition-colors cursor-pointer" onClick={() => setInput('Dame tips de motivación')}>
            <h3 className="font-semibold mb-2">⚡ Motivación</h3>
            <p className="text-sm text-muted-foreground">Mantén el enfoque</p>
          </Card>
        </div>
      </main>
    </div>
  );
};

export default AindyChat;
